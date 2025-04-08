#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#define MAX_STR 1024
#define MAX_CAST 50
#define MAX_LISTED 50
#define TAM_MAX 1024
#define _XOPEN_SOURCE 700


typedef struct {
    char show_ID[MAX_STR];
    char type[MAX_STR];
    char title[MAX_STR];
    char director[MAX_STR];
    char cast[MAX_CAST][MAX_STR];
    int cast_count;
    char country[MAX_STR];
    struct tm data_added;
    int data_added_valid;
    int release_year;
    char rating[MAX_STR];
    char duration[MAX_STR];
    char listed_in[MAX_LISTED][MAX_STR];
    int listed_count;
} Show;

void initShow(Show *show){
    strcpy(show->show_ID, "");
    strcpy(show->type, "");
    strcpy(show->title, "");
    strcpy(show->director, "");
    show->cast_count = 0;
    strcpy(show->country, "");
    memset(&(show->data_added), 0, sizeof(struct tm));
    show->data_added_valid = 0;
    show->release_year = 0;
    strcpy(show->rating, "");
    strcpy(show->duration, "");
    show->listed_count = 0;
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

void setCast(Show *show, const char cast[][MAX_STR], int count) {
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

void setListedIn(Show *show, const char listed_in[][MAX_STR], int count) {
    show->listed_count = count;
    for (int i = 0; i < count && i < MAX_LISTED; i++) {
        strcpy(show->listed_in[i], listed_in[i]);
    }
}

void leCampo(char linha[], int *pos, char buffer[]) {
    int i = 0;
    while (linha[*pos] != ',' && linha[*pos] != '\0') {
        buffer[i++] = linha[*pos];
        (*pos)++;
    }
    buffer[i] = '\0';
    if (linha[*pos] == ','){
        (*pos)++;
    } 
}

void ler(Show *s, const char linha[]) {
    int pos = 0;
    char buffer[MAX_STR];
    
    leCampo((char *)linha, &pos, buffer);
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
    leCampo((char *)linha, &pos, buffer);
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
        leCampo((char *)linha, &pos, buffer);
    }
    setDirector(s, buffer);
    
    // Lê o elenco (cast)
    char Elenco[MAX_CAST][MAX_STR];
    int ator = 0;
    if (linha[pos] == '"') {
        pos++; // pula a aspa inicial
        // Lê cada nome de ator até encontrar a aspa de fechamento
        while (linha[pos] != '"' && linha[pos] != '\0' && ator < MAX_CAST) {
            int i = 0;
            while (linha[pos] != ',' && linha[pos] != '"' && linha[pos] != '\0') {
                Elenco[ator][i++] = linha[pos++];
            }
            Elenco[ator][i] = '\0';
            ator++;
            if (linha[pos] == ',') pos++; // pula a vírgula entre nomes
        }
        if (linha[pos] == '"') pos++; // pula a aspa final
        if (linha[pos] == ',') pos++; // pula a vírgula que separa o campo do próximo
    } else {
        // Se não estiver entre aspas, lê um único nome
        leCampo((char *)linha, &pos, buffer);
        strncpy(Elenco[0], buffer, MAX_STR);
        Elenco[0][MAX_STR-1] = '\0';
        ator = 1;
    }
    setCast(s, Elenco, ator);
    
    // Lê o país
    leCampo((char *)linha, &pos, buffer);
    setCountry(s, buffer);
    
    // Lê a data de adição
    if (linha[pos] == '"') {
        pos++;  // pula a aspa
        int i = 0;
        while (linha[pos] != '"' && linha[pos] != '\0') {
            buffer[i++] = linha[pos++];
        }
        buffer[i] = '\0';
        pos += 2; // pula a aspa final e a vírgula
        
        // Converter a string para data usando strptime (formato: "MMMM dd, yyyy")
        struct tm date;
        memset(&date, 0, sizeof(struct tm));
        if (strptime(buffer, "%B %d, %Y", &date) != NULL) {
            setDataAdded(s, &date);
        }
    } else {
        // Pula o campo sem data
        while (linha[pos] != ',' && linha[pos] != '\0') pos++;
        if (linha[pos] == ',') pos++;
    }
    
    // Lê o ano de lançamento
    int ano = 0;
    while (linha[pos] != ',' && linha[pos] != '\0') {
        ano = ano * 10 + (linha[pos] - '0');
        pos++;
    }
    setReleaseYear(s, ano);
    if (linha[pos] == ',') pos++;
    
    // Lê o rating
    leCampo((char *)linha, &pos, buffer);
    setRating(s, buffer);
    
    // Lê a duração
    leCampo((char *)linha, &pos, buffer);
    setDuration(s, buffer);
    
    // Lê as categorias (listed_in)
    char Categorias[MAX_LISTED][MAX_STR];
    int categoria = 0;
    if (linha[pos] == '"') {
        pos++; // pula a aspa inicial
        while (linha[pos] != '"' && linha[pos] != '\0' && categoria < MAX_LISTED) {
            int i = 0;
            while (linha[pos] != ',' && linha[pos] != '"' && linha[pos] != '\0') {
                Categorias[categoria][i++] = linha[pos++];
            }
            Categorias[categoria][i] = '\0';
            categoria++;
            if (linha[pos] == ',') pos++;
        }
        if (linha[pos] == '"') pos++; // pula a aspa final
    } else {
        // Lê uma única categoria sem aspas
        leCampo((char *)linha, &pos, buffer);
        strncpy(Categorias[0], buffer, MAX_STR);
        Categorias[0][MAX_STR-1] = '\0';
        categoria = 1;
    }
    setListedIn(s, Categorias, categoria);
}

void procuraLinha(int id, char linha[]){
    FILE *bd = fopen("temp/disneyplus.csv", "r");
    if (bd != NULL) {
        bool encontrado = false;
        int contador = 0;
        char buffer[MAX_STR];
        while (fgets(buffer, MAX_STR, bd) != NULL && !encontrado) {
            if (contador == id) {
                strcpy(linha, buffer);  // copia a linha encontrada para o buffer 'linha'
                encontrado = true;
            }
            contador++;
        }
        fclose(bd);
    } else {
        fprintf(stderr, "Erro ao abrir o arquivo temp/disneyplus.csv\n");
        exit(EXIT_FAILURE);
    }    
}

int parseId(char entrada[]){
    int valor = 0;
    int multiplicador = 1;
    for (int i = strlen(entrada) - 1; i >= 0; i--){
        int numero = entrada[i] - '0';
        valor += numero * multiplicador;
        multiplicador *= 10;
    }
    return valor;
}

void imprimir(const Show *s) {
    char dataFormatada[100];

    // Formata a data, se estiver definida
    if (s->data_added_valid) {
        strftime(dataFormatada, sizeof(dataFormatada), "%d/%m/%Y", &s->data_added);
    } else {
        strcpy(dataFormatada, "NaN");
    }

    // Imprime os campos com "##" entre eles
    printf("%s ## %s ## %s ## %s ## ", s->show_ID, s->type, s->title, s->director);

    // Imprime o elenco entre colchetes
    printf("[");
    for (int i = 0; i < s->cast_count; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_count - 1) {
            printf(", ");
        }
    }
    printf("] ## ");

    // Imprime o país, data, ano de lançamento, avaliação e duração
    printf("%s ## %s ## %d ## %s ## %s ## ", s->country, dataFormatada, s->release_year, s->rating, s->duration);

    // Imprime as categorias entre colchetes
    printf("[");
    for (int i = 0; i < s->listed_count; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_count - 1) {
            printf(", ");
        }
    }
    printf("]\n");
}

int main(void) {
    char entrada[TAM_MAX];
    Show shows[100];
    int qtShows = 0;
    
    fgets(entrada, TAM_MAX, stdin);
    entrada[strcspn(entrada, "\n")] = '\0';
    while (strcmp(entrada, "FIM") != 0) {
        int id = parseId(entrada);
        char linha[TAM_MAX];
        procuraLinha(id, linha);
        ler(&shows[qtShows], linha);
        qtShows++;
        // Lê a próxima entrada
        fgets(entrada, TAM_MAX, stdin);
        entrada[strcspn(entrada, "\n")] = '\0';
    }

    for (int i = 0; i < qtShows; i++) {
        imprimir(&shows[i]);
    }
    return 0;
}
