#include <stdio.h>

void escreveArq(int qtElementos){
    // Função responsavel por ler numeros e os escreve no arquivo
    FILE *arq = fopen("num.txt", "w");
    double numero;
    // Utilização do double para maior precisão
    for (int i = 0; i < qtElementos; i++) {
        scanf("%lf", &numero);
        fwrite(&numero, sizeof(double), 1, arq);
    }
    fclose(arq);
}

void leArq(int qtElementos){
    // Função responsavel por ler os numeros escritos no arquivo de forma reversa
    double num;
    FILE *arq = fopen("num.txt", "r");
    for (int i = qtElementos - 1; i >= 0; i--) {
        // move o cursor para a posição do numero
        fseek(arq, i * sizeof(double), SEEK_SET);
        fread(&num, sizeof(num), 1, arq);
        int numeroInteiro = (int) num;
        // Se o número for inteiro, imprime como inteiro
        // caso contrário, imprime o número com a menor precisão possível
        if (numeroInteiro == num) {
            printf("%d\n", numeroInteiro);
        } else {
            printf("%g\n", num);
        }
    }
    fclose(arq);
}


int main() {
    // Função principal responsavel pela chamada das funções e pela leitura de n
    int qtElementos;
    scanf("%d", &qtElementos);
    escreveArq(qtElementos);
    leArq(qtElementos);
    return 0;
}
