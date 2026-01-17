# Diagramma ER - BookRecommender

## Entità e Relazioni del Sistema BookRecommender

```
Diagram_ER

    USERID {
        string userid PK "Chiave primaria"
        string nome_cognome "Nome e cognome utente"
        string codice_fiscale "Codice fiscale univoco"
        string email UK "Email univoca"
        string password "Password hashata"
    }
    
    LIBRI {
        string titolo PK "Titolo del libro (chiave primaria)"
        string autore "Autore del libro"
        string genere "Genere/categoria del libro"
        string editore "Casa editrice"
        int anno "Anno di pubblicazione"
    }
    
    VALUTAZIONI {
        string userid PK,FK "ID utente (chiave esterna)"
        string titolo_libro PK,FK "Titolo libro (chiave esterna)"
        int stile "Valutazione stile (1-5)"
        int contenuto "Valutazione contenuto (1-5)"
        int gradevolezza "Valutazione gradevolezza (1-5)"
        int originalita "Valutazione originalità (1-5)"
        int edizione "Valutazione edizione (1-5)"
        float voto_finale "Voto finale calcolato"
    }
    
    CONSIGLI {
        string userid FK "ID utente che consiglia"
        string libro_referenziale FK "Libro di riferimento"
        string libro_consigliato FK "Libro consigliato"
    }
    
    LIBRERIE {
        int id PK "ID univoco libreria"
        string userid FK "ID utente proprietario"
        string nome_libreria "Nome della libreria personale"
        string libro FK "Titolo del libro nella libreria"
    }
    
    %% Relazioni
    USERID ||-->{ VALUTAZIONI : "valuta"
    LIBRI ||-->{ VALUTAZIONI : "riceve_valutazioni"
    
    USERID ||-->{ CONSIGLI : "fornisce_consigli"
    LIBRI ||-->{ CONSIGLI : "libro_referenziale"
    LIBRI ||-->{ CONSIGLI : "libro_consigliato"
    
    USERID ||-->{ LIBRERIE : "possiede"
    LIBRI ||-->{ LIBRERIE : "contenuto_in"
    
```

## Descrizione delle Entità

### 1. **USERID** (Utenti)
- **Chiave primaria**: `userid` (stringa)
- **Attributi**:
  - `nome_cognome`: Nome e cognome dell'utente
  - `codice_fiscale`: Codice fiscale univoco
  - `email`: Email univoca per l'utente
  - `password`: Password hashata per sicurezza

### 2. **LIBRI** (Catalogo Libri)
- **Chiave primaria**: `titolo` (stringa)
- **Attributi**:
  - `autore`: Nome dell'autore
  - `genere`: Categoria/genere del libro
  - `editore`: Casa editrice
  - `anno`: Anno di pubblicazione

### 3. **VALUTAZIONI** (Valutazioni Utenti)
- **Chiave primaria composta**: `(userid, titolo_libro)`
- **Chiavi esterne**: 
  - `userid` → USERID.userid
  - `titolo_libro` → LIBRI.titolo
- **Attributi di valutazione** (tutti da 1 a 5):
  - `stile`: Valutazione dello stile di scrittura
  - `contenuto`: Valutazione del contenuto
  - `gradevolezza`: Valutazione della gradevolezza
  - `originalita`: Valutazione dell'originalità
  - `edizione`: Valutazione dell'edizione
  - `voto_finale`: Media delle valutazioni (calcolata automaticamente)

### 4. **CONSIGLI** (Raccomandazioni)
- **Chiavi esterne**:
  - `userid` → USERID.userid
  - `libro_referenziale` → LIBRI.titolo
  - `libro_consigliato` → LIBRI.titolo
- **Funzione**: Permette agli utenti di consigliare libri basandosi su un libro di riferimento

### 5. **LIBRERIE** (Librerie Personali)
- **Chiave primaria**: `id` (auto-incrementale)
- **Chiavi esterne**:
  - `userid` → USERID.userid
  - `libro` → LIBRI.titolo
- **Attributi**:
  - `nome_libreria`: Nome della libreria personale
  - `libro`: Titolo del libro contenuto nella libreria

## Relazioni

1. **USERID ↔ VALUTAZIONI**: Un utente può valutare molti libri (1:N)
2. **LIBRI ↔ VALUTAZIONI**: Un libro può essere valutato da molti utenti (1:N)
3. **USERID ↔ CONSIGLI**: Un utente può fornire molti consigli (1:N)
4. **LIBRI ↔ CONSIGLI**: Un libro può essere sia referenziale che consigliato (N:N)
5. **USERID ↔ LIBRERIE**: Un utente può possedere molte librerie (1:N)
6. **LIBRI ↔ LIBRERIE**: Un libro può essere contenuto in molte librerie (N:N)

## Vincoli di Integrità

- **Vincoli di dominio**: Le valutazioni devono essere comprese tra 1 e 5
- **Vincoli di unicità**: 
  - Email univoca per utente
  - Codice fiscale univoco per utente
  - Combinazione univoca (userid, nome_libreria, libro) per librerie
- **Vincoli di integrità referenziale**: Tutte le chiavi esterne devono riferirsi a record esistenti
- **Cascata**: L'eliminazione di un utente elimina tutte le sue valutazioni, consigli e librerie
