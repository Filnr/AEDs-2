import java.util.*;

class Celula {
    // Funções comums de uma celula encadeada em 4 direções
    private int elem;
    public Celula inf, sup, prox, ant;

    public Celula() {
        elem = 0;
        inf = sup = prox = ant = null;
    }

    public Celula(int elem) {
        this.elem = elem;
        inf = sup = prox = ant = null;
    }

    public Celula(int elem, Celula inf, Celula sup, Celula prox, Celula ant) {
        this.elem = elem;
        this.inf = inf;
        this.sup = sup;
        this.prox = prox;
        this.ant = ant;
    }

    public int getElem() {
        return elem;
    }

    public void setElem(int elem) {
        this.elem = elem;
    }
}

class Matriz {
    private Celula primeiro;
    private int linha, coluna;

    public Matriz() {
        primeiro = null;
        linha = coluna = 0;
    }

    public Matriz(int linha, int coluna) {
        if (linha > 0 && coluna > 0) {
            this.linha = linha;
            this.coluna = coluna;
            iniciaMatriz();
        }
    }

    public int getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    public Celula getPrimeiro() {
        return primeiro;
    }

    public void iniciaMatriz() {
        Celula[][] tmp = new Celula[linha][coluna];
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                // Inicia cada posição da matriz
                tmp[i][j] = new Celula();
            }
        }
        // Conecta cada celula da matriz uma as outras
        for(int i = 0; i < linha; i++){
            for(int j = 0; j < coluna; j++){
                if (i > 0) {
                    tmp[i][j].sup = tmp[i - 1][j];
                }
                if (i < linha - 1) {
                    tmp[i][j].inf = tmp[i + 1][j];
                }
                if (j > 0) {
                    tmp[i][j].ant = tmp[i][j - 1];
                }
                if (j < coluna - 1) {
                    tmp[i][j].prox = tmp[i][j + 1];
                }
            }
        }
        this.primeiro = tmp[0][0];
    }

    public Celula buscaCelula(int linha, int coluna) {
        Celula atual = primeiro;
        // Avança de cima para baixo até a posição
        for (int i = 0; i < linha; i++) {
            atual = atual.inf;
        }
        // Avança da esquerda para a direita até a posição 
        for (int i = 0; i < coluna; i++) {
            atual = atual.prox;
        }
        return atual;
    }

    public void inserirElem(int elemento, int linha, int coluna) {
        if (linha >= 0 && linha < this.linha && coluna >= 0 && coluna < this.coluna) {
            Celula buscado = buscaCelula(linha, coluna);
            buscado.setElem(elemento);
        }

    }

    public int buscaValor(int linha, int coluna) {
        // Busca o valor de uma posição da matriz, se não encontrada retorna o valor simbolico -404;
        int buscado = -404;
        if (linha >= 0 && linha < this.linha && coluna >= 0 && coluna < this.coluna) {
            buscado = buscaCelula(linha, coluna).getElem();
        }
        return buscado;
    }

    public void imprimir(){
        // Imprime a matriz ja formatada
        for(int i = 0; i < linha; i++){
            for(int j = 0; j < coluna; j++){
                if(j > 0){
                    System.out.printf(" ");
                }
                System.out.print(buscaValor(i, j));
            }
            System.out.println(" ");
        }
    }

    private boolean saoIguais(Matriz b) {
        return (this.linha == b.getLinha() && this.coluna == b.getColuna());
    }

    public Matriz soma(Matriz somado) {
        // Função responsavel por realizar a soma de matrizes
        Matriz resultado = new Matriz();
        // Se as matrizes forem incompativeis para soma, é retornado uma matriz nula
        if (saoIguais(somado)) {
            resultado = new Matriz(linha, coluna);
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    // Soma o valor das posiçãoes xy de cada matriz para inserir na nova matriz
                    int soma = buscaValor(i, j) + somado.buscaValor(i, j);
                    resultado.inserirElem(soma, i, j);
                }
            }
        }
        return resultado;
    }

    public Matriz multiplicacao(Matriz multiplicado){
        // Função responsavel por realizar a multiplicação de matrizes
        Matriz resultado = new Matriz();
        // Verifica se a matriz é apropriada para a multiplicação
        if(coluna == multiplicado.getLinha()){
            resultado = new Matriz(linha, coluna);
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    int acumulador = 0;
                    for(int k = 0; k < coluna; k++){
                        acumulador += this.buscaValor(i, k) * multiplicado.buscaValor(k, j);
                    }
                    resultado.inserirElem(acumulador, i, j);
                }
            }
        }
        return resultado;
    }

    public void mostrarDiagonalPrincipal(){
        int tamDiagonal = Math.min(linha, coluna);
        for(int i = 0; i < tamDiagonal; i++){
            if(i > 0){
                // Condicional para imprimir espaço apenas entre os numeros
                System.out.printf(" ");
            }
            System.out.print(buscaValor(i, i));
        }
        System.out.println(" ");
    }

    public void mostrarDiagonalSecundaria(){
        int tamDiagonal = Math.min(linha, coluna);
        for(int i = 0; i < tamDiagonal; i++){
            int j = tamDiagonal - i - 1;
            if(i > 0){
                // Condicional para imprimir espaço apenas entre os numeros
                System.out.printf(" ");
            }
            System.out.print(buscaValor(i, j));
        }
        System.out.println(" ");
    }

}

public class Principal {
    public static void main(String[] args){
        Scanner ler = new Scanner(System.in, "UTF-8");
        int n = Integer.parseInt(ler.nextLine());
        // Entrada e registro de dados
        for(int i = 0; i < n; i++){
            // Leitura da primeira Matriz
            int l1 = ler.nextInt();
            int c1 = ler.nextInt();
            Matriz primeira = new Matriz(l1, c1);
            for(int j = 0; j < l1; j++){
                for(int k = 0; k < c1; k++){
                    int valor = ler.nextInt();
                    primeira.inserirElem(valor, j, k);
                }
            }
            // Leitura da segunda Matriz
            int l2 = ler.nextInt();
            int c2 = ler.nextInt();
            Matriz segunda = new Matriz(l2, c2);
            for(int j = 0; j < l2; j++){
                for(int k = 0; k < c2; k++){
                    int valor = ler.nextInt();
                    segunda.inserirElem(valor, j, k);
                }
            }
            // Inicio da saida;
            primeira.mostrarDiagonalPrincipal();
            primeira.mostrarDiagonalSecundaria();
            Matriz soma = primeira.soma(segunda);
            soma.imprimir();
            Matriz multiplicacao = primeira.multiplicacao(segunda);
            multiplicacao.imprimir();
        }
        ler.close();
    }
}
