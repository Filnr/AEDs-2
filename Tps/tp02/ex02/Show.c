#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#define MAX_STR 1024
#define MAX_CAST 50
#define MAX_LISTED 50

typedef struct {
    char show_ID[MAX_STRING];
    char type[MAX_STRING];
    char title[MAX_STRING];
    char director[MAX_STRING];
    char cast[MAX_CAST][MAX_STRING];
    int cast_count;
    char country[MAX_STRING];
    struct tm data_added;
    int release_year;
    char rating[MAX_STRING];
    char duration[MAX_STRING];
    char listed_in[MAX_LISTED][MAX_STRING];
    int listed_count;
} Show;

void initShow(Show *show){
    strcpy(show->show_ID, "");
    strcpy(s->type, "");
    strcpy(s->title, "");
    strcpy(s->director, "");
    s->cast_count = 0;
    strcpy(s->country, "");
    memset(&(s->data_added), 0, sizeof(struct tm));
    s->data_added_valid = 0;
    s->release_year = 0;
    strcpy(s->rating, "");
    strcpy(s->duration, "");
    s->listed_count = 0;
}

void setShowID(Show *show, const char id[]){
    strcpy(show->show_ID, id);
}

void setType(Show *show, const char *type) {
    strcpy(show->type, type);
}

void setTitle(Show *show, const char *title) {
    strcpy(show->title, title);
}

void setDirector(Show *show, const char *director) {
    strcpy(show->director, director);
}

void setCast(Show *show, const char cast[][MAX_STR_SIZE], int count) {
    show->cast_count = count;
    for (int i = 0; i < count && i < MAX_CAST; i++) {
        strcpy(show->cast[i], cast[i]);
    }
}

void setCountry(Show *show, const char *country) {
    strcpy(show->country, country);
}

void setDataAdded(Show *show, const struct tm *date) {
    show->data_added = *date; // copia a data
    show->data_added_valid = 1;
}

void setReleaseYear(Show *show, int year) {
    show->release_year = year;
}

void setRating(Show *show, const char *rating) {
    strcpy(show->rating, rating);
}

void setDuration(Show *show, const char *duration) {
    strcpy(show->duration, duration);
}

void setListedIn(Show *show, const char listed_in[][MAX_STR_SIZE], int count) {
    show->listed_count = count;
    for (int i = 0; i < count && i < MAX_LISTED; i++) {
        strcpy(show->listed_in[i], listed_in[i]);
    }
}

void leCampo(char linha[], int *pos, char buffer[]) {
    int i = 0;
    while (linha[*pos] != ',' && linha[*pos] != '\0') {
        buffer[i++] = line[*pos];
        (*pos)++;
    }
    buffer[i] = '\0';
    if (line[*pos] == ','){
        (*pos)++;
    } 
}

void ler(Show *s, const char linha[]) {
    int pos = 0;
    char buffer[MAX_STR_SIZE];
    
    leCampo(line, &pos, buffer);
    setShowID(s, buffer);
    
    // Lê o tipo: se o primeiro caractere for 'M', então é "Movie"
    if (linha[pos] == 'M') {
        setType(s, "Movie");
        pos += 6;  // pula "Movie" e a vírgula (ajuste conforme o formato real)
    } else {
        setType(s, "TV Show");
        pos += 8;  // pula "TV Show" e a vírgula
    }
    
    // Lê o título
    readField(linha, &pos, buffer);
    setTitle(s, buffer);
    
    // Lê o diretor
    // Se o campo estiver entre aspas, pula a aspa inicial, lê até a aspa final e pula-a
    if (linha[pos] == '"') {
        pos++;  // pula a aspa
        int i = 0;
        while (linha[pos] != '"' && linha[pos] != '\0') {
            buffer[i] = linha[pos];
            i++;
            pos++;
        }
        buffer[i] = '\0';
        pos += 2;  // pula a aspa final e a vírgula
    } else {
        readField(line, &pos, buffer);
    }
    setDirector(s, buffer);
    
    // Lê o elenco (cast)
    char Elenco[MAX_CAST][MAX_STR_SIZE];
    int castCount = 0;
    if (line[pos] == '"') {
        pos++; // pula a aspa inicial
        // Lê cada nome de ator até encontrar a aspa de fechamento
        while (line[pos] != '"' && line[pos] != '\0' && castCount < MAX_CAST) {
            int i = 0;
            while (line[pos] != ',' && line[pos] != '"' && line[pos] != '\0') {
                castFields[castCount][i++] = line[pos++];
            }
            castFields[castCount][i] = '\0';
            castCount++;
            if (line[pos] == ',') pos++; // pula a vírgula entre nomes
        }
        if (line[pos] == '"') pos++; // pula a aspa final
        if (line[pos] == ',') pos++; // pula a vírgula que separa o campo do próximo
    } else {
        // Se não estiver entre aspas, lê um único nome
        readField(line, &pos, buffer);
        strncpy(castFields[0], buffer, MAX_STR_SIZE);
        castFields[0][MAX_STR_SIZE-1] = '\0';
        castCount = 1;
    }
    setCast(s, castFields, castCount);
    
    // Lê o país
    readField(line, &pos, buffer);
    setCountry(s, buffer);
    
    // Lê a data de adição
    // Se estiver entre aspas, lê até a aspa final; em seguida, converte a string para struct tm.
    if (line[pos] == '"') {
        pos++;  // pula a aspa
        int i = 0;
        while (line[pos] != '"' && line[pos] != '\0') {
            buffer[i++] = line[pos++];
        }
        buffer[i] = '\0';
        pos += 2; // pula a aspa final e a vírgula
        
        // Converter a string para data (exemplo usando strptime, se disponível)
        struct tm date;
        memset(&date, 0, sizeof(struct tm));
        // Supondo que a data esteja no formato "MMMM dd, yyyy" (ajuste conforme necessário)
        // Por exemplo, se a data for "September 3, 2021", o formato pode ser "%B %d, %Y"
        if (strptime(buffer, "%B %d, %Y", &date) != NULL) {
            setDataAdded(s, &date);
        }
    } else {
        // pula o campo sem data
        while (line[pos] != ',' && line[pos] != '\0') pos++;
        if (line[pos] == ',') pos++;
    }
    
    // Lê o ano de lançamento
    int ano = 0;
    while (line[pos] != ',' && line[pos] != '\0') {
        ano = ano * 10 + (line[pos] - '0');
        pos++;
    }
    setReleaseYear(s, ano);
    if (line[pos] == ',') pos++;
    
    // Lê o rating
    readField(line, &pos, buffer);
    setRating(s, buffer);
    
    // Lê a duração
    readField(line, &pos, buffer);
    setDuration(s, buffer);
    
    // Lê as categorias (listed_in)
    char listedFields[MAX_LISTED][MAX_STR_SIZE];
    int listedCount = 0;
    if (line[pos] == '"') {
        pos++; // pula a aspa inicial
        while (line[pos] != '"' && line[pos] != '\0' && listedCount < MAX_LISTED) {
            int i = 0;
            while (line[pos] != ',' && line[pos] != '"' && line[pos] != '\0') {
                listedFields[listedCount][i++] = line[pos++];
            }
            listedFields[listedCount][i] = '\0';
            listedCount++;
            if (line[pos] == ',') pos++;
        }
        if (line[pos] == '"') pos++; // pula a aspa final
    } else {
        // Lê uma única categoria sem aspas
        readField(line, &pos, buffer);
        strncpy(listedFields[0], buffer, MAX_STR_SIZE);
        listedFields[0][MAX_STR_SIZE-1] = '\0';
        listedCount = 1;
    }
    setListedIn(s, listedFields, listedCount);
}

int main(void){
    
}
