#define _XOPEN_SOURCE 700
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <ctype.h>
#include <stdbool.h>
#include <time.h>
#include <locale.h>

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

void imprimir_show(const Show *s, int tam) {
    char date_str[32];
    if (s->data_added == (time_t)-1) {
        strcpy(date_str, "NaN");
    } else {
        struct tm *tm_ptr = localtime(&s->data_added);
        // %-d: dia sem padding (sem zero ou espaço à esquerda)
        strftime(date_str, sizeof(date_str), "%B %-d, %Y", tm_ptr);
    }

    // Monta a string de "cast" com o formato "[elem1, elem2, ...]"
    char elenco[1024] = "[";
    if (s->cast) {
        for (int i = 0; s->cast[i] != NULL; i++) {
            strcat(elenco, s->cast[i] ? s->cast[i] : "NaN");
            if (s->cast[i + 1] != NULL)
                strcat(elenco, ", ");
        }
    }
    strcat(elenco, "]");

    // Monta a string de "listed_in"
    char categorias[1024] = "[";
    if (s->listed_in) {
        for (int i = 0; s->listed_in[i] != NULL; i++) {
            strcat(categorias, s->listed_in[i] ? s->listed_in[i] : "NaN");
            if (s->listed_in[i + 1] != NULL)
                strcat(categorias, ", ");
        }
    }
    strcat(categorias, "]");

    printf("[%d] => %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
        tam,
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

void separa_e_ordena(char ***destino, char *texto) {
    int quantidade = 1;
    // conta a quantidade de elementos o array possui, por meio da ','
    for (int i = 0; texto[i] != '\0'; i++) {
        if (texto[i] == ',') quantidade++;
    
    }
    *destino = malloc(sizeof(char *) * (quantidade + 1));  // +1 para o NULL no final

    int indice = 0;
    char *token = strtok(texto, ",");
    while (token != NULL) {
        // Tira espaços no começo
        while (*token == ' ') token++;  
        // Aloca memória para cada string separada
        (*destino)[indice++] = strdup(token);  
        token = strtok(NULL, ",");
    }
    (*destino)[indice] = NULL; // Finaliza com NULL

    // Ordena o array de strings
    ordena(*destino, quantidade);
}

time_t converter_data(const char *texto) {
    if (texto == NULL || texto[0] == '\0') return (time_t)-1;
    struct tm tm_data = {0};
    if (strptime(texto, "%B %d, %Y", &tm_data) == NULL) return (time_t)-1;
    return mktime(&tm_data);
}

void interpreta(Show *s, char *linha) {
    char campo_temp[1024];
    int posicao = 0;
    int dentro_aspas = 0;
    int indice_campo = 0;
    char *campos[20];
    int i;

    // Inicializa o array campos;
    for (i = 0; i < 20; i++) {
        campos[i] = NULL;
    }

    campos[0] = malloc(1024);
    posicao = 0;

    for (i = 0; linha[i] != '\0'; i++) {
        char letra = linha[i];
        if (letra == '"') {
            dentro_aspas = !dentro_aspas;
        } else if (letra == ',' && !dentro_aspas) {
            campos[indice_campo][posicao] = '\0';
            indice_campo++;
            campos[indice_campo] = malloc(1024);
            posicao = 0;
        } else {
            campos[indice_campo][posicao++] = letra;
        }
    }
    campos[indice_campo][posicao] = '\0';

    // Distribuição dos seus respectivos campos para seus atributos
    s->show_ID = strdup(campos[0]);
    s->type = (strcasecmp(campos[1], "movie") == 0) ? strdup("Movie") : strdup("TV Show");
    s->title = strdup(campos[2]);
    s->director = (campos[3][0] == '\0') ? strdup("NaN") : strdup(campos[3]);
    s->country = (campos[5][0] == '\0') ? strdup("NaN") : strdup(campos[5]);
    s->rating = strdup(campos[8]);
    s->duration = strdup(campos[9]);
    // Analisa e array de strings, e decide se é vazio ou não, se não for, é preparado e ordenado
    if (campos[4][0] == '\0') {
        s->cast = malloc(sizeof(char *) * 2);
        s->cast[0] = strdup("NaN");
        s->cast[1] = NULL;
    } else {
        separa_e_ordena(&s->cast, campos[4]);
    }
    if (campos[10][0] == '\0') {
        s->listed_in = malloc(sizeof(char *) * 2);
        s->listed_in[0] = strdup("NaN");
        s->listed_in[1] = NULL;
    } else {
        separa_e_ordena(&s->listed_in, campos[10]);
    }

    // Converte o texto de data no tipo data
    s->data_added = converter_data(campos[6]);

    // Trata release_year
    s->release_year = (campos[7][0] == '\0') ? 0 : atoi(campos[7]);

    // Libera a memória temporária
    for (i = 0; i <= indice_campo; i++) {
        free(campos[i]);
    }
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

void clone(Show *original, Show *clone){
    init_show(clone); // Sempre inicialize antes

    clone->show_ID = strdup(original->show_ID);
    clone->type = strdup(original->type);
    clone->title = strdup(original->title);
    clone->director = strdup(original->director);
    clone->country = strdup(original->country);
    clone->data_added = original->data_added;
    clone->release_year = original->release_year;
    clone->rating = strdup(original->rating);
    clone->duration = strdup(original->duration);
    // primeiro copia a contagem, para depois conseguir copiar completamente cada parte
    clone->cast_count = original->cast_count;
    clone->cast = malloc(clone->cast_count * sizeof(char*));
    for (int i = 0; i < clone->cast_count; i++) {
        clone->cast[i] = strdup(original->cast[i]);
    }
    clone->listed_count = original->listed_count;
    clone->listed_in = malloc(clone->listed_count * sizeof(char*));
    for (int i = 0; i < clone->listed_count; i++) {
        clone->listed_in[i] = strdup(original->listed_in[i]);
    }
}

typedef struct No{
    Show show;
    struct No *esq;
    struct No *dir;
    int altura;
} No;

typedef struct {
    No* raiz;
    // Salva as comparações realizadas na pesquisa
    int comps;
} Arvore;

No* newNo(Show show){
    No* nova = (No*)malloc(sizeof(No));
    nova->show = show;
    nova->esq = NULL;
    nova->dir = NULL;
    nova->altura = 0;
    return nova;
}

void iniciarArvore(Arvore *arvore){
    arvore->raiz = NULL;
    arvore->comps = 0;
}

int altura(No *no){
    // Retorna a altura do no
    int altura;
    if(no == NULL){
        // quando o no é null, ele é altura -1
        altura = -1;
    }
    else{
        altura = no->altura;
    }
    return altura;
}

int maior(int a, int b) {
    return (a > b) ? a : b;
}

void atualizaAltura(No *no){
    // Função responsavel por atualizar a altura do no avl
    if(no != NULL){
        no->altura = 1 + maior(altura(no->esq), altura(no->dir));
    }
}

int fatorBalanceamento(No *no){
    // Função responsavel pelo calculo de balanceamento de cada no
    if(no == NULL){
        return 0;
    }
    return altura(no->esq) - altura(no->dir);
}

No* rotacionaDir(No *no){
    // Realiza a rotação
    No *noEsq = no->esq;
    No *noEsqDir = noEsq->dir;
    noEsq->dir = no;
    no->esq = noEsqDir;
    // Atualiza a altura do no avó e pai
    atualizaAltura(no);
    atualizaAltura(noEsq);
    return noEsq;
}

No* rotacionaEsq(No *no){
    No *noDir = no->dir;
    No *noDirEsq = noDir->esq;
    noDir->esq = no;
    no->dir = noDirEsq;
    // Atualiza os nos alterados
    atualizaAltura(no);
    atualizaAltura(noDir);
    return noDir;
}

No* inserirRec(No *atual, Show show){
    if(atual == NULL){
        return newNo(show);
    }

    if(strcmp(show.title, atual->show.title) < 0) {
        atual->esq = inserirRec(atual->esq, show);
    }
    else if(strcmp(show.title, atual->show.title) > 0){
        atual->dir = inserirRec(atual->dir, show);
    }
    else{
        return atual;
    }
    // Atualiza as alturas de cada no
    atualizaAltura(atual);
    int fator = fatorBalanceamento(atual);
    // Inserção ocorre na esquerda, e esta desequilibrando para a esquerda
    if(fator > 1 && strcmp(show.title, atual->esq->show.title) < 0) {
        return rotacionaDir(atual);
    }
    // Inserção ocorre na direita, e esta desequilibrando para a direita
    if(fator < -1 && strcmp(show.title, atual->dir->show.title) > 0){
        return rotacionaEsq(atual);
    }
    // Balanceamento de esquerda-direita
    if(fator > 1 && strcmp(show.title, atual->esq->show.title) > 0){
        atual->esq = rotacionaEsq(atual->esq);
        return rotacionaDir(atual);
    }
    // Balanceamento de direita-esquerda
    if(fator < -1 && strcmp(show.title, atual->dir->show.title) < 0){
        atual->dir = rotacionaDir(atual->dir);
        return rotacionaEsq(atual);
    }
    return atual;
}

void inserir(Arvore *arvore, Show show){
    // Chama a função recursiva de inserir
    arvore->raiz = inserirRec(arvore->raiz, show);
}

bool pesquisarRec(No *atual, char title[], Arvore *arvore){
    if(atual == NULL){
        // Não encontrado
        return false;
    }
    arvore->comps++;
    // Realiza as comparações e salva em uma variavel para evitar bugs
    int cmp = strcmp(title, atual->show.title);
    if(cmp < 0){
        printf("esq ");
        return pesquisarRec(atual->esq, title, arvore);
    } else if(cmp > 0){
        printf("dir ");
        return pesquisarRec(atual->dir, title, arvore);
    } else {
        return true;
    }
}

bool pesquisar(Arvore *arvore, char title[]){
    // Imprime a saida, e chama o pesquisar que é recursivo
    printf("raiz ");
    return pesquisarRec(arvore->raiz, title, arvore);
}

void liberarNosArvore(No* no) {
    if (no != NULL) {
        // Visita recursivamente o filho esquerdo para liberar sua subárvore
        liberarNosArvore(no->esq);
        // Visita recursivamente o filho direito para liberar sua subárvore
        liberarNosArvore(no->dir);
        free(no);
    }
}

void freeArvore(Arvore *arvore) {
    if (arvore == NULL) {
        // Nada para destruir se a árvore for NULL
        return;
    }
    // Chama a função auxiliar recursiva, começando pela raiz da árvore.
    liberarNosArvore(arvore->raiz);
    arvore->raiz = NULL;
}

int main() {
    clock_t inicio = clock();
    setlocale(LC_TIME, "C");
    char entrada[MAX_LINE];
    Arvore arvore;
    iniciarArvore(&arvore);
    // Se a entrada tiver '\n' troca por '\0' que é a representação de fim da string e conseguir comparar corretamente as strings
    fgets(entrada, MAX_LINE, stdin);
    size_t len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    // Parte responsavel por ler a primeira parte da entrada
    while(!ehFim(entrada)) {
        Show show;
        show_from_id(&show, entrada);
        inserir(&arvore, show);
        fgets(entrada, MAX_LINE, stdin);
        len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    }
    fgets(entrada, MAX_LINE, stdin);
    len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    while(!ehFim(entrada)){
        if(pesquisar(&arvore, entrada)){
            printf("SIM\n");
        }
        else{
            printf("NAO\n");
        }
        fgets(entrada, MAX_LINE, stdin);
        len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    }
        freeArvore(&arvore);
     // calculo final do tempo, e impressão de todos os dados para o log
    clock_t fim = clock();
    double tempo = ((double) (fim - inicio) / CLOCKS_PER_SEC) * 1000;
    FILE* log = fopen("869899_avl.txt", "w");
    fprintf(log, "869899\t%d\t%.4lf", arvore.comps, tempo);
    return 0;
}
