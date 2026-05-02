package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Charge les variables d'environnement depuis un fichier .env.
 * Aucune valeur par défaut : toutes les variables doivent être définies dans .env
 * (ou en variables d'environnement système). Sinon une exception est levée.
 */
public final class EnvConfig {

    private static final String ENV_FILE = "config.env";
    private static volatile Map<String, String> env = null;

    /** 
     * Constructeur privé pour éviter l'instanciation 
     * @throws IOException si le fichier .env n'est pas trouvé ou non accessible
     * @throws IllegalStateException si la variable requise est manquante
     * @throws NumberFormatException si la variable requise n'est pas un entier valide
     * @throws SecurityException si le fichier .env n'est pas accessible
     */
    private EnvConfig() {}

    /**
     * Charge le fichier .env une seule fois. Les variables système (System.getenv) ont priorité.
     */
    public static synchronized void load() {
        if (env != null) {
            return;
        }
        Map<String, String> loaded = new HashMap<>();
        Path path = Paths.get(ENV_FILE);
        if (Files.exists(path) && Files.isReadable(path)) {
            try {
                Files.readAllLines(path).forEach(line -> {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) return;
                    int eq = line.indexOf('=');
                    if (eq > 0) {
                        String key = line.substring(0, eq).trim();
                        String value = line.substring(eq + 1).trim();
                        value = stripInlineComment(value);
                        if ((value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2)
                                || (value.startsWith("'") && value.endsWith("'") && value.length() >= 2)) {
                            value = value.substring(1, value.length() - 1);
                        }
                        loaded.put(key, value);
                    }
                });
            } catch (IOException e) {
                throw new IllegalStateException("Impossible de lire " + ENV_FILE + " : " + e.getMessage(), e);
            }
        }
        env = Collections.unmodifiableMap(loaded);
    }

    private static String stripInlineComment(String value) {
        StringBuilder sb = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                sb.append(c);
                continue;
            }
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                sb.append(c);
                continue;
            }
            if (!inSingleQuote && !inDoubleQuote && (c == '#' || c == ';')) {
                break;
            }
            sb.append(c);
        }
        return sb.toString().trim();
    }

    /** 
     * Valeur obligatoire (fichier .env ou variable d'environnement). Lance si absente ou vide. 
     * @param key La clé de la variable requise
     * @return La valeur de la variable requise
     * @throws IllegalStateException si la variable requise est manquante
     */
    private static String getRequired(String key) {
        if (env == null) load();
        String value = System.getenv(key);
        if (value != null && !value.isEmpty()) return value;
        value = env.get(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Variable requise manquante : " + key + " (définir dans .env ou variables d'environnement)");
        }
        return value;
    }

    /** 
     * Entier obligatoire. Lance si absent, vide ou invalide. 
     * @param key La clé de la variable requise
     * @return La valeur de la variable requise
     * @throws IllegalStateException si la variable requise est manquante
     * @throws NumberFormatException si la variable requise n'est pas un entier valide
     */
    private static int getRequiredInt(String key) {
        String v = getRequired(key);
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Variable " + key + " doit être un entier, valeur actuelle : " + v, e);
        }
    }

    // --- Serveur RMI ---
    public static int getRmiPort() {
        return getRequiredInt("RMI_PORT");
    }

    public static String getRmiServiceName() {
        return getRequired("RMI_SERVICE_NAME");
    }

    public static String getRmiHost() {
        return getRequired("RMI_HOST");
    }

    public static int getRmiTimeout() {
        return getRequiredInt("TIMEOUT");
    }

    // --- Configuration de la base de données ---
    public static String getDbUrl() {
        return getRequired("DB_URL");
    }

    public static String getDbName() {
        return getRequired("DB_NAME");
    }

    public static String getDbUser() {
        return getRequired("DB_USER");
    } 

    // --- Règles de validation (mot de passe, question secrète, tentatives) ---
    public static int getMinPasswordLength() {
        return getRequiredInt("MIN_PASSWORD_LENGTH");
    }

    public static int getMinQuestionLength() {
        return getRequiredInt("MIN_QUESTION_LENGTH");
    }

    public static int getMinQuestionLengthStrict() {
        return getRequiredInt("MIN_QUESTION_LENGTH_STRICT");
    }

    public static int getMinReponseLength() {
        return getRequiredInt("MIN_REPONSE_LENGTH");
    }

    public static int getMaxRetryAttempts() {
        return getRequiredInt("MAX_RETRY_ATTEMPTS");
    }

    // --- Déconnexion automatique (inactivité) ---
    /** Délai d'inactivité avant déconnexion automatique (en minutes). */
    public static int getInactivityTimeoutMinutes() {
        return getRequiredInt("INACTIVITY_TIMEOUT_MINUTES");
    }

    /** Période de vérification de l'inactivité (en secondes). */
    public static int getCheckIntervalSeconds() {
        return getRequiredInt("CHECK_INTERVAL_SECONDS");
    }

    /** Email d'envoi des notifications (optionnel). Si absent, les notifications par email sont désactivées. */
    public static String getNotificationEmail() {
        if (env == null) load();
        String v = System.getenv("USER_EMAIL");
        if (v != null && !v.isEmpty()) return v;
        v = env.get("USER_EMAIL");
        return (v != null && !v.isEmpty()) ? v.trim() : null;
    }

    /** Mot de passe de l'email d'envoi (optionnel, pour SMTP). */
    public static String getNotificationPassword() {
        if (env == null) load();
        String v = System.getenv("USER_PASSWORD");
        if (v != null && !v.isEmpty()) return v;
        v = env.get("USER_PASSWORD");
        return (v != null && !v.isEmpty()) ? v.trim() : null;
    }

    /** Serveur SMTP (optionnel). Défaut : smtp.gmail.com */
    public static String getSmtpHost() {
        if (env == null) load();
        String v = System.getenv("SMTP_HOST");
        if (v != null && !v.isEmpty()) return v.trim();
        v = env.get("SMTP_HOST");
        return (v != null && !v.isEmpty()) ? v.trim() : "smtp.gmail.com";
    }

    /** Port SMTP (optionnel). Défaut : 587 */
    public static int getSmtpPort() {
        if (env == null) load();
        String v = System.getenv("SMTP_PORT");
        if (v == null || v.isEmpty()) v = env.get("SMTP_PORT");
        if (v == null || v.isEmpty()) return 587;
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return 587;
        }
    }

    /** Timeout SMTP en millisecondes (optionnel). Défaut : 15000 */
    public static int getSmtpTimeoutMs() {
        if (env == null) load();
        String v = System.getenv("SMTP_TIMEOUT_MS");
        if (v == null || v.isEmpty()) v = env.get("SMTP_TIMEOUT_MS");
        if (v == null || v.isEmpty()) return 15_000;
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return 15_000;
        }
    }
}
