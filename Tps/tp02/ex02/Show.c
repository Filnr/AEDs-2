#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

#define MAX_LINE 1024
#define MAX_SHOWS 500

typedef struct {
    char *show_ID;
    char *type;
    char *title;
    char *director;
    char **cast;
    int cast_count;
    char *country;
    char *data_added;
    int release_year;
    char *rating;
    char *duration;
    char **listed_in;
    int listed_count;
} Show;


// Inicializa a estrutura com valores padrão 
void init_show(Show *s) {
    s->show_ID = NULL;
    s->type = NULL;
    s->title = NULL;
    s->director = NULL;
    s->cast = NULL;
    s->cast_count = 0;
    s->country = NULL;
    s->data_added = NULL;
    s->release_year = 0;
    s->rating = NULL;
    s->duration = NULL;
    s->listed_in = NULL;
    s->listed_count = 0;
}

// Libera toda a memória alocada para um objeto Show
void free_show(Show *s) {
    if(s->show_ID) free(s->show_ID);
    if(s->type) free(s->type);
    if(s->title) free(s->title);
    if(s->director) free(s->director);
    if(s->country) free(s->country);
    if(s->data_added) free(s->data_added);
    if(s->rating) free(s->rating);
    if(s->duration) free(s->duration);
    if(s->cast) {
       for (int i = 0; i < s->cast_count; i++) {
            free(s->cast[i]);
       }
       free(s->cast);
    }
    if(s->listed_in) {
       for (int i = 0; i < s->listed_count; i++) {
            free(s->listed_in[i]);
       }
       free(s->listed_in);
    }
}

int converteStr(const char *entrada) {
    int len = strlen(entrada);
    int valor = 0;
    int multiplicador = 1;
    for (int i = len - 1; i > 0; i--) {
        int numero = entrada[i] - '0';
        valor += numero * multiplicador;
        multiplicador *= 10;
    }
    return valor;
}

// Verifica se a entrada é exatamente "FIM"
int ehFim(const char *entrada) {
    return (strcmp(entrada, "FIM") == 0);
}

// Conta quantos segmentos (atores ou categorias) existem, contando as vírgulas até o fechamento da aspa.
int contaAtores(const char *line, int pos) {
    int count = 1;
    int len = strlen(line);
    while (pos < len && line[pos] != '"') {
        if (line[pos] == ',') count++;
        pos++;
    }
    return count;
}

// Ordena alfabeticamente um array de strings
void ordenaAlfa(char **array, int count) {
    int trocou;
    do {
        trocou = 0;
        for (int i = 0; i < count - 1; i++) {
            if (strcmp(array[i], array[i+1]) > 0) {
                char *temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
                trocou = 1;
            }
        }
        count--;
    } while(trocou);
}

void imprimir_show(const Show *s) {
    // Ordena os arrays "cast" e "listed_in"
    if (s->cast_count > 1)
        ordenaAlfa(s->cast, s->cast_count);
    if (s->listed_count > 1)
        ordenaAlfa(s->listed_in, s->listed_count);
    
    // Monta a string de "cast" com o formato "[elem1, elem2, ...]"
    char castStr[1024] = "[";
    for (int i = 0; i < s->cast_count; i++) {
        strcat(castStr, s->cast[i]);
        if (i < s->cast_count - 1)
            strcat(castStr, ", ");
    }
    strcat(castStr, "]");
    
    // Monta a string de "listed_in"
    char listedStr[1024] = "[";
    for (int i = 0; i < s->listed_count; i++) {
        strcat(listedStr, s->listed_in[i]);
        if (i < s->listed_count - 1)
            strcat(listedStr, ", ");
    }
    strcat(listedStr, "]");
    
    // Se a data não foi definida, imprime "NaN"
    char *dataFormatted = (s->data_added != NULL) ? s->data_added : "NaN";
    
    printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
        s->show_ID ? s->show_ID : "",
        s->title ? s->title : "",
        s->type ? s->type : "",
        s->director ? s->director : "",
        castStr,
        s->country ? s->country : "",
        dataFormatted,
        s->release_year,
        s->rating ? s->rating : "",
        s->duration ? s->duration : "",
        listedStr);
}

void parse_line(Show *s, const char *line) {
    int pos = 0;
    int len = strlen(line);
    char buffer[1024];
    int bufIndex;

    // ID
    bufIndex = 0;
    while (pos < len && line[pos] != ',') {
        buffer[bufIndex++] = line[pos++];
    }
    buffer[bufIndex] = '\0';
    s->show_ID = strdup(buffer);
    pos++;

    // Tipo
    // Se a primeira letra for M, é movie, se não TV show
    if (pos < len && line[pos] == 'M') {
        s->type = strdup("Movie");
        pos += 6;
        // pula o numero de posições a depender da palvra
    } else {
        s->type = strdup("TV Show");
        pos += 8;
    }
    
    // Título
    bufIndex = 0;
    memset(buffer, 0, sizeof(buffer));
    if (line[pos] == '"') {
        pos++;
        while (pos < len && line[pos] != ',') {
            if (line[pos] != '"') {
                buffer[bufIndex++] = line[pos];
            }
            pos++;
        }
    } else {
        while (pos < len && line[pos] != ',') {
            buffer[bufIndex++] = line[pos++];
        }
    }
    buffer[bufIndex] = '\0';
    s->title = strdup(buffer);
    pos++;

    // Diretor
    bufIndex = 0;
    memset(buffer, 0, sizeof(buffer));
    if (pos < len && line[pos] != ',') {
        if (line[pos] == '"') {
            pos++;
            while (pos < len && line[pos] != '"') {
                buffer[bufIndex++] = line[pos++];
            }
            buffer[bufIndex] = '\0';
            pos += 2;
        } else {
            while (pos < len && line[pos] != ',') {
                buffer[bufIndex++] = line[pos++];
            }
            buffer[bufIndex] = '\0';
            pos++;
        }
    } else {
        // Caso o show não tenha diretor ou outro campus, preenche como NaN
        strcpy(buffer, "NaN");
        pos++;
    }
    s->director = strdup(buffer);

    // Elenco (cast)
    if (pos < len && line[pos] != ',') {
        if (line[pos] == '"') {
            pos++;
            int count = contaAtores(line, pos);
            s->cast_count = count;
            s->cast = (char**) malloc(count * sizeof(char*));
            // Inicializa cada elemento com string vazia
            for (int i = 0; i < count; i++) {
                s->cast[i] = strdup("");
            }
            int ator = 0;
            while (pos < len && line[pos] != '"') {
                if (line[pos] != ',' && line[pos] != '"') {
                    // Acrescenta o caractere à string atual
                    int len_old = strlen(s->cast[ator]);
                    char temp[2] = { line[pos], '\0' };
                    char *newStr = (char*) malloc(len_old + 2);
                    strcpy(newStr, s->cast[ator]);
                    strcat(newStr, temp);
                    free(s->cast[ator]);
                    s->cast[ator] = newStr;
                    pos++;
                } else if (line[pos] == ',') {
                    pos += 2;
                    ator++;
                } else {
                    pos++;
                }
            }
            if (pos < len && line[pos] == '"')
                pos += 2;
        } else {
            // Se não estiver entre aspas, existe apenas um elemento
            s->cast_count = 1;
            s->cast = (char**) malloc(sizeof(char*));
            bufIndex = 0;
            memset(buffer, 0, sizeof(buffer));
            while (pos < len && line[pos] != ',') {
                buffer[bufIndex++] = line[pos++];
            }
            buffer[bufIndex] = '\0';
            s->cast[0] = strdup(buffer);
            if (pos < len) pos++;
        }
    } else {
        s->cast_count = 1;
        s->cast = (char**) malloc(sizeof(char*));
        s->cast[0] = strdup("NaN");
        pos++;
    }

    // País
    bufIndex = 0;
    memset(buffer, 0, sizeof(buffer));
    if (pos < len && line[pos] != ',') {
        while (pos < len && line[pos] != ',') {
            buffer[bufIndex++] = line[pos++];
        }
        buffer[bufIndex] = '\0';
        pos++;
    } else {
        strcpy(buffer, "NaN");
        pos++;
    }
    s->country = strdup(buffer);

    // Data de adição (date)
    bufIndex = 0;
    memset(buffer, 0, sizeof(buffer));
    if (pos < len && line[pos] == '"') {
        pos++;
        while (pos < len && line[pos] != '"') {
            buffer[bufIndex++] = line[pos++];
        }
        buffer[bufIndex] = '\0';
        pos += 2;
        s->data_added = strdup(buffer);
    } else {
        s->data_added = NULL;
        pos++;
    }

    // Ano de lançamento (release_year)
    int ano = 0;
    if (pos < len && line[pos] != ',') {
        int decimal = 1000;
        while (pos < len && line[pos] != ',') {
            int numero = line[pos] - '0';
            ano += numero * decimal;
            pos++;
            decimal /= 10;
        }
        pos++;
    } else {
        pos++;
    }
    s->release_year = ano;

    // Avaliação (rating)
    bufIndex = 0;
    memset(buffer, 0, sizeof(buffer));
    while (pos < len && line[pos] != ',') {
        buffer[bufIndex++] = line[pos++];
    }
    buffer[bufIndex] = '\0';
    pos++;
    s->rating = strdup(buffer);

    // Duração (duration)
    bufIndex = 0;
    memset(buffer, 0, sizeof(buffer));
    while (pos < len && line[pos] != ',') {
        buffer[bufIndex++] = line[pos++];
    }
    buffer[bufIndex] = '\0';
    pos++;
    s->duration = strdup(buffer);

    // Categorias (listed_in)
    if (pos < len) {
        if (line[pos] != '"') {
            s->listed_count = 1;
            s->listed_in = (char**) malloc(sizeof(char*));
            bufIndex = 0;
            memset(buffer, 0, sizeof(buffer));
            while (pos < len && line[pos] != ',') {
                buffer[bufIndex++] = line[pos++];
            }
            buffer[bufIndex] = '\0';
            s->listed_in[0] = strdup(buffer);
        } else {
            pos++;
            int count = contaAtores(line, pos);
            s->listed_count = count;
            s->listed_in = (char**) malloc(count * sizeof(char*));
            for (int i = 0; i < count; i++) {
                s->listed_in[i] = strdup("");
            }
            int categoria = 0;
            while (pos < len && line[pos] != '"' && categoria < count) {
                if (line[pos] != ',' && line[pos] != '"') {
                    int len_old = strlen(s->listed_in[categoria]);
                    char temp[2] = { line[pos], '\0' };
                    char *newStr = (char*) malloc(len_old + 2);
                    strcpy(newStr, s->listed_in[categoria]);
                    strcat(newStr, temp);
                    free(s->listed_in[categoria]);
                    s->listed_in[categoria] = newStr;
                    pos++;
                } else if (line[pos] == ',') {
                    pos += 2;
                    categoria++;
                } else {
                    pos++;
                }
            }
        }
    }
}

void show_from_id(Show *s, int id) {
    // Construtor responsavel por buscar o ID
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) {
        perror("Erro ao abrir o arquivo");
        exit(1);
    }
    char line[MAX_LINE];
    int contador = 0;
    bool encontrado = false;
    while (fgets(line, MAX_LINE, fp) && !encontrado) {
        // Remove o caractere de nova linha, se existir
        size_t l = strlen(line);
        if(l > 0 && line[l-1]=='\n')
            line[l-1] = '\0';
        if (contador == id) {
            parse_line(s, line);
            encontrado = true;
        }
        contador++;
    }
    fclose(fp);
    if (!encontrado) {
        // Caso o id digitado seja invalido, inicianliza sem valor
        init_show(s);
    }
}

void clone(Show *original, Show *clone){
    clone->show_ID = original->show_ID;
    clone->type = original->type;
    clone->title = original->title;
    clone->director = original->director;
    for (int i = 0; i < original->cast_count; i++) {
        clone->cast[i] = original->cast[i];
    }
    clone->cast_count = original->cast_count;
    clone->country = original->country;
    clone->data_added = original->data_added;
    clone->release_year = original->release_year;
    clone->rating = original->rating;
    clone->duration = original->duration;
    for (int i = 0; i < original->listed_count; i++) {
        clone->listed_in[i] = original->listed_in[i];
    }
    clone->listed_count = original->listed_count;
}

int main() {
    char entrada[MAX_LINE];
    Show shows[MAX_SHOWS];
    int tam = 0;
    
    // Inicializa todos os objetos no array
    for (int i = 0; i < MAX_SHOWS; i++) {
        init_show(&shows[i]);
    }
    
    // Leitura de entradas
    fgets(entrada, MAX_LINE, stdin);
    while(!ehFim(entrada)) {
        
        size_t len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
        int id = converteStr(entrada);
        show_from_id(&shows[tam], id);
        tam++;
        fgets(entrada, MAX_LINE, stdin);
    }
    
    // Impressão dos shows
    for (int i = 0; i < tam; i++) {
        imprimir_show(&shows[i]);
        free_show(&shows[i]);
    }
    return 0;
}
