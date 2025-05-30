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

void imprimir_show(const Show *s) {
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

int encontrarMaiorAno(Show shows[], int tam) {
    // Função para encontrar o maior release_year no vetor
    int maior = shows[0].release_year;
    for (int i = 1; i < tam; i++) {
        if (shows[i].release_year > maior) {
            maior = shows[i].release_year;
        }
    }
    return maior;
}


void countingSortPorDigito(Show shows[], int tam, int exp, int *comp, int *mov) {
    // Counting Sort por dígito específico
    Show *saida = malloc(tam * sizeof(Show));
    int contagem[10] = {0};

    // Conta a ocorrencia de cada digito
    for (int i = 0; i < tam; i++) {
        int digito = (shows[i].release_year / exp) % 10;
        contagem[digito]++;
        (*mov)++;
    }

    // Soma cumulativa
    for (int i = 1; i < 10; i++) {
        contagem[i] += contagem[i - 1];
        (*mov)++;
        (*comp)++;
    }

    // Constroi o vetor de saída (de trás para frente para manter estabilidade)
    for (int i = tam - 1; i >= 0; i--) {
        int digito = (shows[i].release_year / exp) % 10;
        int pos = contagem[digito] - 1;
        saida[pos] = shows[i];
        contagem[digito]--;
        (*mov) += 2; // Uma movimentação para colocar + atualizar contagem
        (*comp)++;
    }

    // Copia o vetor de saída de volta para o vetor original
    for (int i = 0; i < tam; i++) {
        shows[i] = saida[i];
        (*mov)++;
        (*comp)++;
    }

    free(saida);
}

/*
 * Implementação do Radix Sort para a estrutura Show
 * 
 * O Radix Sort é um algoritmo de ordenação estável, baseado em ordenar
 * os numeros por unidades, começando do menor(ou menos significativo) até o maior
 * 
 * Estrutura:
 * - Para cada casa decimal (1, 10, 100, 1000), é aplicada uma ordenação
 *   usando Counting Sort adaptado para os dígitos.
 * - Após a ordenação pelo ano, realiza-se a ordenação secundária pelo
 *   título (title) em caso de anos iguais.
 * 
 * Complexidade:
 * - O Radix Sort possui complexidade O(d * (n + k)), onde:
 *      d = número de dígitos(fixo em 4 para funcionar para release year),
 *      n = quantidade de elementos,
 *      k = base do sistema (10 para decimal).
 * 
 * Pontos fortes:
 * - Bom para conjuntos de numeros pequenos.
 * - É éstavel.
 * 
 * Pontos fracos:
 * - Utiliza espaço extra proporcional ao tamanho do vetor.
 * - Restrito a tipos de dados numéricos ou adaptados.
 */
void radixSort(Show shows[], int tam, int *comp, int *mov) {
    int maior = encontrarMaiorAno(shows, tam);

    // Aplica countingSort para cada unidade
    for (int exp = 1; maior / exp > 0; exp *= 10) {
        countingSortPorDigito(shows, tam, exp, comp, mov);
    }

    // Após ordenar por ano, desempatar por título
    for (int i = 1; i < tam; i++) {
        Show temp = shows[i];
        int j = i - 1;
        while (j >= 0 && shows[j].release_year == temp.release_year &&
               strcasecmp(shows[j].title, temp.title) > 0) {
            shows[j + 1] = shows[j];
            j--;
            (*comp)++;
            (*mov)++;
        }
        shows[j + 1] = temp;
        (*mov)++;
    }
}

int main() {
    setlocale(LC_TIME, "C");
    char entrada[MAX_LINE];
    Show shows[MAX_SHOWS];
    int tam = 0;
    clock_t inicio = clock();
    // Inicializa todos os objetos no array
    for (int i = 0; i < MAX_SHOWS; i++) {
        init_show(&shows[i]);
    }

    // Se a entrada tiver '\n' troca por '\0' que é a representação de fim da string e conseguir comparar corretamente as strings
    fgets(entrada, MAX_LINE, stdin);
    size_t len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    while(!ehFim(entrada)) {
        int id = converteStr(entrada);
        show_from_id(&shows[tam], id);
        tam++;
        fgets(entrada, MAX_LINE, stdin);
        len = strlen(entrada);
        if(len > 0 && entrada[len-1]=='\n'){
            entrada[len-1] = '\0';
        }
    }
    int comps = 0, mov = 0;
    // Chama o RadixSort para ordenar o array de shows
    radixSort(shows, tam, &comps, &mov);
    // libera a memoria alocada
    for (int i = 0; i < tam; i++) {
        imprimir_show(&shows[i]);
        free_show(&shows[i]);
    }
    // calculo final do tempo, e impressão de todos os dados para o log
    clock_t fim = clock();
    double tempo = ((double) (fim - inicio) / CLOCKS_PER_SEC) * 1000;
    FILE* log = fopen("matricula_radixsort.txt", "w");
    fprintf(log, "869899\t%d\t%d\t%.4lf", comps, mov, tempo);
    return 0;
}
