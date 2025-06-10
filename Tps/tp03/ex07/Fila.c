#define _XOPEN_SOURCE 700
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <ctype.h>
#include <stdbool.h>
#include <time.h>
#include <locale.h>
#include <math.h>

#define MAX_LINE 1024
#define MAX_SHOWS 500
#define TAM_FILA 5
#define MAX_CAMPOS 20

typedef struct {
    char *show_ID;
    char *type;
    char *title;
    char *director;
    char **cast;
    int cast_count;
    char *country;
    time_t data_added;
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
    s->data_added = (time_t)-1;
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

void imprimir_show(const Show *s) {
    // Formata os arrays e o date para string
    char date_str[32];
    if (s->data_added == (time_t)-1) {
        strcpy(date_str, "NaN");
    } else {
        struct tm *tm_ptr = localtime(&s->data_added);
        if (tm_ptr == NULL) {
            strcpy(date_str, "NaN");
        } else {
            strftime(date_str, sizeof(date_str), "%B %-d, %Y", tm_ptr);
        }
    }

    char elenco[1024];
    strcpy(elenco, "[");
    if (s->cast) {
        for (int i = 0; i < s->cast_count; i++) {
            strcat(elenco, s->cast[i] ? s->cast[i] : "NaN");
            if (i < s->cast_count - 1)
                strcat(elenco, ", ");
        }
    }
    strcat(elenco, "]");

    char categorias[1024];
    strcpy(categorias, "[");
    if (s->listed_in) {
    for (int i = 0; i < s->listed_count; i++) {
        strcat(categorias, s->listed_in[i] ? s->listed_in[i] : "NaN");
        if (i < s->listed_count - 1)
            strcat(categorias, ", ");
    }
}
strcat(categorias, "]");

    printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
        s->show_ID ? s->show_ID : "NaN",
        s->title ? s->title : "NaN",
        s->type ? s->type : "NaN",
        s->director ? s->director : "NaN",
        elenco,
        s->country ? s->country : "NaN",
        date_str,
        s->release_year,
        s->rating ? s->rating : "NaN",
        s->duration ? s->duration : "NaN",
        categorias);
}


void ordena(char *array[], int tam) {
    // Ordena um array de string em ordem alfabetica
    int trocou;
    do {
        trocou = 0;
        for (int i = 0; i < tam - 1; i++) {
            if (strcmp(array[i], array[i+1]) > 0) {
                char *temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
                trocou = 1;
            }
        }
        tam--;
    } while(trocou);
}

int separa_e_ordena(char ***destino, char *texto) {
    int quantidade = 1;
    // Conta quantos elementos existem no texto, separando por vírgula
    for (int i = 0; texto[i] != '\0'; i++) {
        if (texto[i] == ',') quantidade++;
    }

    *destino = malloc(sizeof(char *) * quantidade);

    int indice = 0;
    char *token = strtok(texto, ",");
    while (token != NULL) {
        while (*token == ' ') token++;  // Remove espaço no começo
        (*destino)[indice++] = strdup(token);
        token = strtok(NULL, ",");
    }

    ordena(*destino, quantidade);
    return quantidade;
}

time_t converter_data(const char *texto) {
    // Converte data em formato padrão para o time
    if (texto == NULL || texto[0] == '\0') return (time_t)-1;
    struct tm tm_data = {0};
    if (strptime(texto, "%B %d, %Y", &tm_data) == NULL) return (time_t)-1;
    return mktime(&tm_data);
}

int parse_csv_line(const char *linha, char campos[][MAX_LINE]) {
    // Função responsavel por separar e converter os campos do csv
    int campo = 0, pos = 0;
    bool dentro_aspas = false;

    for (int i = 0; linha[i] != '\0'; i++) {
        char c = linha[i];

        if (c == '"') {
            dentro_aspas = !dentro_aspas;
        } else if (c == ',' && !dentro_aspas) {
            campos[campo][pos] = '\0';
            campo++;
            pos = 0;
            if (campo >= MAX_CAMPOS) break; // proteção
        } else {
            if (pos < MAX_LINE - 1) {
                campos[campo][pos++] = c;
            }
        }
    }
    campos[campo][pos] = '\0'; // finaliza último campo
    return campo + 1; // total de campos
}

static void interpreta(Show *s, char *linha) {
    char campos[MAX_CAMPOS][MAX_LINE];
    int num_campos = parse_csv_line(linha, campos);
    // Parte responsavel por pegar a parte correspondente dos campos para os atributos
    s->show_ID = strdup(campos[0]);
    s->type = strcasecmp(campos[1], "movie") == 0 ? strdup("Movie") : strdup("TV Show");
    s->title = strdup(campos[2]);
    s->director = (*campos[3]) ? strdup(campos[3]) : strdup("NaN");
    s->country = (*campos[5]) ? strdup(campos[5]) : strdup("NaN");
    s->rating = strdup(campos[8]);
    s->duration = strdup(campos[9]);

    if (*campos[4]) 
        s->cast_count = separa_e_ordena(&s->cast, campos[4]);
    else {
        s->cast = malloc(sizeof(char*));
        s->cast[0] = strdup("NaN");
        s->cast_count = 1;
    }

    if (*campos[10]) 
        s->listed_count = separa_e_ordena(&s->listed_in, campos[10]);
    else {
        s->listed_in = malloc(sizeof(char*));
        s->listed_in[0] = strdup("NaN");
        s->listed_count = 1;
    }

    s->data_added = converter_data(campos[6]);
    s->release_year = (*campos[7]) ? atoi(campos[7]) : 0;
}

void show_from_id(Show *s, char *entrada) {
    int id = converteStr(entrada);
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
            interpreta(s, line);
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

Show clone(Show *original){
    Show clone;
    init_show(&clone); // Sempre inicialize antes

    clone.show_ID = strdup(original->show_ID);
    clone.type = strdup(original->type);
    clone.title = strdup(original->title);
    clone.director = strdup(original->director);
    clone.country = strdup(original->country);
    clone.data_added = original->data_added;
    clone.release_year = original->release_year;
    clone.rating = strdup(original->rating);
    clone.duration = strdup(original->duration);
    // primeiro copia a contagem, para depois conseguir copiar completamente cada parte
    clone.cast_count = original->cast_count;
    clone.cast = malloc(clone.cast_count * sizeof(char*));
    for (int i = 0; i < clone.cast_count; i++) {
        clone.cast[i] = strdup(original->cast[i]);
    }
    clone.listed_count = original->listed_count;
    clone.listed_in = malloc(clone.listed_count * sizeof(char*));
    for (int i = 0; i < clone.listed_count; i++) {
        clone.listed_in[i] = strdup(original->listed_in[i]);
    }
    return clone;
}

typedef struct No{
    Show show;
    struct No *prox;
} No;

typedef struct {
    No *p;
    No *u;
    int tam;
} FilaCircular;

bool filaCheia(FilaCircular *fila){
    // Função responsavel por comparar para ver se a fila esta cheia
    return (fila->tam == TAM_FILA);
}

bool filaVazia(FilaCircular *fila){
    return (fila->tam == 0);
}

void iniciarLista(FilaCircular *lista) {
    // Inicia a lista com o formato padrão de listas
    lista->u = NULL;
    lista->p = NULL;
    lista->tam = 0;
}

No* newNo(Show* show){
    // Construtor de No
    No* nova = (No*)malloc(sizeof(No));
    nova->show = *show;
    nova->prox = NULL;
    return nova;
}

Show remover(FilaCircular *fila){
    // remove o primeiro elemento da fila
   if (filaVazia(fila)) {
        fprintf(stderr, "Erro: fila vazia.\n");
        exit(1);
    }
    No *rem = fila->p;
    Show removido = rem->show;
    fila->p = rem->prox;
    // Fila ficou vazia
    if (fila->p == NULL) fila->u = NULL;
    // Libera o ponteiro de rem, já que não sera mais utilizado
    free(rem);
    fila->tam--;
    return removido;
}

void inserir(FilaCircular *fila, Show show){
    // Insere o novo show no final da fila, e se estiver cheia(com 5 elementos), remove 1
    if(filaCheia(fila)){
        remover(fila);
    }
    No* novo = newNo(&show);
    // Verificação necessária para garantir a formatação correta da lista
    if (filaVazia(fila)) {
        fila->p = fila->u = novo;
    } else {
        fila->u->prox = novo;
        fila->u = novo;
    }
    fila->tam++;
}

void imprimirFila(FilaCircular *fila) {
    // Função responsavel por imprimir o shows da fila, imprimir a posição do show
    No* atual = fila->p;
    int i = 0;
    while(atual != NULL){
        printf("[%d] ", i);
        imprimir_show(&atual->show);
        atual = atual->prox;
        i++;
    }
}

void calcularMedia(FilaCircular *fila){
    if (fila->tam == 0) {
        printf("[Media] 0\n");
        return;
    }
    int soma = 0;
    // Loop começa com o primeiro No, e vai avançando enquanto soma o release_year
    No* atual = fila->p;
    while(atual != NULL){
        soma += atual->show.release_year;
        atual = atual->prox;
    }
    int media = soma/ fila->tam;
    printf("[Media] %d\n",media);
}

int main() {
    char entrada[MAX_LINE];
    FilaCircular fila;
    iniciarLista(&fila);
    Show show;
    // Se a entrada tiver '\n' troca por '\0' que é a representação de fim da string e conseguir comparar corretamente as strings
    fgets(entrada, MAX_LINE, stdin);
    size_t len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    // Parte responsavel por ler a primeira parte da entrada
    while(!ehFim(entrada)) {
        show_from_id(&show, entrada);
        inserir(&fila, clone(&show));
        free_show(&show);
        calcularMedia(&fila);
        fgets(entrada, MAX_LINE, stdin);
        len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    }
    // Inicio da leitura da 2 parte
    int n;
    scanf("%d", &n);
    getchar();
    int ind = 0;
    for(int i = 0; i < n; i++){
        fgets(entrada, MAX_LINE, stdin);
        len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
        // Condicional responsavel por identificar qual é o comando
        if(entrada[0] == 'I') {
            char id[MAX_LINE];
            int i = 2;
            int j = 0;
            // Copia a parte do id na entrada para uma variavel
            while(entrada[i] != '\0'){
                id[j] = entrada[i];
                j++;
                i++;
            }
            id[j] = '\0';
            show_from_id(&show, id);
            inserir(&fila, show);
            // Imprime a media da fila com o novo elemento
            calcularMedia(&fila);
        }
        else{
            Show removido = remover(&fila);
            printf("(R) %s\n", removido.title);
        }
    }
    // libera a memoria alocada e impressão da pilha ao desempilhar
    imprimirFila(&fila);
    return 0;
}
