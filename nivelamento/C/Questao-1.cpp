#include <iostream>

bool temElemento(int vetor[], int procurado, int tam)
{
    int i = 0;
    bool encontrado = false;
    while (i < tam && !encontrado)
    {
        if(vetor[i] == procurado)
        {
            encontrado = true;
        }
        i++;
    }
    return encontrado;
}

int main(void)
{
    int procurado, tam = 10;
    int vetor[tam] = {22, 1, 20, 99, 102, 7, 9, 10, 15, 109};
    std::cout << "Digite o numero a ser procurado: ";
    std::cin >> procurado;
    bool encontrado = temElemento(vetor, procurado, tam);
    if(encontrado)
    {
        std::cout << "Elemento presente no array" << std::endl;
    }
    else 
        std::cout << "Elemento não presente no array" << std::endl;
    return 0;
}