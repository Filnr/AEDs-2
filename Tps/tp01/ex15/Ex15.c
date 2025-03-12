#include <stdio.h>

int main() {
    FILE *arquivo = fopen("dados.bin", "wb");
    int quantidade, parteInteira;
    float valor;

    scanf("%d", &quantidade);

    for (int i = 0; i < quantidade; i++) {
        scanf("%f", &valor);
        fwrite(&valor, sizeof(float), 1, arquivo);
    }

    fclose(arquivo);

    arquivo = fopen("dados.bin", "rb");
    fseek(arquivo, 0, SEEK_END);

    for (int i = quantidade - 1; i >= 0; i--) {
        fseek(arquivo, i * sizeof(float), SEEK_SET);
        fread(&valor, sizeof(float), 1, arquivo);

        parteInteira = (int)valor;

        if (parteInteira == valor) {
            printf("%d\n", parteInteira);
        } else {
            printf("%g\n", valor);
        }
    }

    fclose(arquivo);
    return 0;
}
