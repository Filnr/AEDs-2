import java.util.*;
import java.text.*;
import java.io.*;

//simple date format e date
class Show {
    private String show_ID;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    public void setID(String id) {
        this.show_ID = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCast(String[] cast) {
        this.cast = (cast != null) ? cast.clone() : null;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDate(Date date_added) {
        this.date_added = date_added;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setListed(String[] listed_in) {
        this.listed_in = (listed_in != null) ? listed_in.clone() : null;
    }

    public void setRelease(int year) {
        this.release_year = year;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDirector() {
        return this.director;
    }

    public String[] getCast() {
        return this.cast;
    }

    public String getCountry() {
        return this.country;
    }

    public Date getDate_added() {
        return this.date_added;
    }

    public String getDuration() {
        return this.duration;
    }

    public String[] getListed_in() {
        return listed_in;
    }

    public String getRating() {
        return rating;
    }

    public String getShow_ID() {
        return show_ID;
    }

    public int getRelease_year() {
        return release_year;
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    public Show() {
        show_ID = "NaN";
        type = "NaN";
        title = "NaN";
        director = "NaN";
        cast = new String[] { "NaN" };
        country = "NaN";
        date_added = null;
        release_year = 0;
        rating = "NaN";
        duration = "NaN";
        listed_in = new String[] { "NaN" };
    }

    private int extraiId(String entrada) {
        // Converte a entrada que esta em formato de string, para um valor int que será
        // usado como chave para ler o csv
        // Proteção contra entradas vazias ou null
        if (entrada == null || entrada.length() <= 1) {
            return 0;
        }
        
        int valor = 0;
        int multiplicador = 1;
        for (int i = entrada.length() - 1; i > 0; i--) {
            char c = entrada.charAt(i);
            // Verifica se o caractere é um dígito antes de converter
            if (Character.isDigit(c)) {
                int numero = c - '0';
                valor += numero * multiplicador;
                multiplicador *= 10;
            }
        }
        return valor;
    }

    public Show(String entrada) {
        // Construtor responsavel por ler a linha exata do id lido, e chamar a função
        // responsavel pela leitura e atribuição
        // Inicializa com valores padrão para evitar nulls
        this();
        
        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }
        
        String caminho = "/tmp/disneyplus.csv";
        int id = extraiId(entrada);
        String linha = "";
        int contador = 0;   
        boolean encontrado = false;
        try (BufferedReader ler = new BufferedReader(new FileReader(caminho))) {
            while ((linha = ler.readLine()) != null && !encontrado) {
                if (contador == id) {
                    ler(linha);
                    encontrado = true;
                }
                contador++;
            }
        } catch (IOException erro) {
            erro.printStackTrace();
            // Mantém os valores padrão inicializados no construtor
        }
    }

    public void ler(String linha) {
        // Verifica se a linha não é null ou vazia
        if (linha == null || linha.trim().isEmpty()) {
            return;
        }
        
        // Inicaliza uma lista de Strings que contera cada campo da linha
        List<String> listaCampos = new ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean temAspas = false;
        for (int i = 0; i < linha.length(); i++) {
            char letra = linha.charAt(i);
            // Estrutura responsavel por interpretar o inicio e fim de cada campo, e
            // adicionar as letras
            if (letra == '"') {
                temAspas = !temAspas;
            } else if (letra == ',' && !temAspas) {
                listaCampos.add(campoAtual.toString());
                campoAtual.setLength(0);
            } else {
                campoAtual.append(letra);
            }
        }
        listaCampos.add(campoAtual.toString());
        
        // Verifica se há campos suficientes para evitar IndexOutOfBoundsException
        if (listaCampos.size() < 11) {
            return;
        }
        
        // Adição dos campos aos seus respectivos atributos
        String[] campos = new String[listaCampos.size()];
        campos = listaCampos.toArray(campos);
        this.show_ID = campos[0];
        this.type = campos[1].trim().equalsIgnoreCase("movie") ? "Movie" : "TV Show";
        this.title = campos[2];
        this.director = campos[3].equals("") ? "NaN" : campos[3];
        this.cast = campos[4].equals("") ? new String[] { "NaN" } : campos[4].split(", ");
        if (this.cast.length > 1) {
            Ordena(this.cast);
        }
        this.country = campos[5].equals("") ? "NaN" : campos[5];
        try {
            this.date_added = campos[6].equals("") ? null : dateFormat.parse(campos[6]);
        } catch (Exception e) {
            this.date_added = null;
        }
        // Proteção contra NumberFormatException
        try {
            this.release_year = campos[7].equals("") ? 0 : Integer.parseInt(campos[7]);
        } catch (NumberFormatException e) {
            this.release_year = 0;
        }
        this.rating = campos[8];
        this.duration = campos[9];
        this.listed_in = campos[10].equals("") ? new String[] { "NaN" } : campos[10].split(", ");
        if (this.listed_in.length > 1) {
            Ordena(this.listed_in);
        }
    }

    public Show clone() {
        // Metodo responsavel por criar um clone do objeto selecionado
        Show clonado = new Show();
        clonado.show_ID = this.show_ID;
        clonado.type = this.type;
        clonado.title = this.title;
        clonado.director = this.director;
        // Proteção contra null pointer ao clonar arrays
        clonado.cast = (this.cast != null) ? this.cast.clone() : new String[] { "NaN" };
        clonado.country = this.country;
        clonado.date_added = this.date_added;
        clonado.duration = this.duration;
        clonado.rating = this.rating;
        clonado.release_year = this.release_year;
        clonado.listed_in = (this.listed_in != null) ? this.listed_in.clone() : new String[] { "NaN" };
        return clonado;
    }

    public void Ordena(String[] vetor) {
        // Função responsavel por ordenar em ordem alfabetica um vetor de strings
        // Ordenção por seleção
        // Proteção contra array null ou vazio
        if (vetor == null || vetor.length <= 1) {
            return;
        }
        
        for (int i = 0; i < vetor.length; i++) {
            int indiceMin = i;
            for (int j = (i + 1); j < vetor.length; j++) {
                // Proteção contra elementos null no array
                if (vetor[j] != null && vetor[indiceMin] != null && 
                    vetor[j].compareTo(vetor[indiceMin]) < 0) {
                    indiceMin = j;
                }
            }
            if (indiceMin != i) {
                String temp = vetor[i];
                vetor[i] = vetor[indiceMin];
                vetor[indiceMin] = temp;
            }
        }
    }

    public void imprimir() {
        // antes de começar a impressão, ordena os vetores que precisam ser ordenados
        SimpleDateFormat date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dateFormatada;
        if (getDate_added() != null) {
            dateFormatada = date.format(getDate_added());
        } else {
            dateFormatada = "NaN";
        }
        
        // Proteção contra arrays null
        String castStr = (cast != null) ? Arrays.toString(cast) : "[NaN]";
        String listedStr = (listed_in != null) ? Arrays.toString(listed_in) : "[NaN]";
        
        System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
                show_ID, title, type, director, castStr, country, dateFormatada, release_year,
                rating, duration, listedStr);
    }
}

class Celula {
    // Metodos padrões de Celula
    private Show show;
    public Celula prox;
    public Celula ant;

    public Celula() {
        this.show = null;
        this.prox = null;
        this.ant = null;
    }

    public Celula(Show elem) {
        this.show = elem;
        this.prox = null;
        this.ant = null;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public boolean empty() {
        // Verifica se a celula esta vazia
        return (show == null && prox == null && ant == null);
    }
}

class Lista {
    private Celula primeiro;
    private Celula ultimo;
    private int tam;
    private int movs;
    private int comps;

    public Lista() {
        // Inicaliza a lista
        this.primeiro = null;
        this.ultimo = null;
        this.tam = 0;
        this.movs = 0;
        this.comps = 0;
    }

    public boolean empty() {
        // Verifica se a lista ta vazia
        return primeiro == null;
    }

    public void inserir(Show elem) {
        // Proteção contra elemento null
        if (elem == null) {
            return;
        }
        
        // Insere as novas celulas no fim da lista
        Celula novaCelula = new Celula(elem);
        if (empty()) {
            primeiro = novaCelula;
            ultimo = novaCelula;
        } else {
            novaCelula.ant = ultimo;
            ultimo.prox = novaCelula;
            ultimo = novaCelula;
        }
        tam++;
    }

    private Celula obterCelula(int pos) {
        // Proteção contra posição inválida
        if (pos < 0 || pos >= tam || empty()) {
            return null;
        }
        
        // Procura na lista, a celula da posição pos
        Celula atual;
        int i = 0;
        // Para aumentar a eficiencia, se pos for maior que que o meio, começa a busca
        // do ultimo para atras
        if (pos < tam / 2) {
            atual = primeiro;
            while (i < pos && atual != null) {
                atual = atual.prox;
                i++;
            }
        } else {
            atual = ultimo;
            i = tam - 1;
            while (i > pos && atual != null) {
                atual = atual.ant;
                i--;
            }
        }
        return atual;
    }

    private void swap(Celula i, Celula j) {
        // Função de swap
        // Proteção contra células null ou vazias
        if (i != null && j != null && !i.empty() && !j.empty()) {
            Show tmp = i.getShow();
            i.setShow(j.getShow());
            j.setShow(tmp);
        }
    }

    private int compareShows(Show atual, Show prox) {
        // Função responsavel por realizar a comparação entre date_added, com desempate
        // no titulo
        // Proteção contra shows null
        if (atual == null || prox == null) {
            return 0;
        }
        
        // Proteção contra datas null
        if (atual.getDate_added() == null && prox.getDate_added() == null) {
            return atual.getTitle().trim().compareToIgnoreCase(prox.getTitle().trim());
        }
        if (atual.getDate_added() == null) {
            return -1;
        }
        if (prox.getDate_added() == null) {
            return 1;
        }
        
        int diff = Long.compare(atual.getDate_added().getTime(), prox.getDate_added().getTime());
        if (diff == 0) {
            diff = atual.getTitle().trim().compareToIgnoreCase(prox.getTitle().trim());
        }
        return diff;
    }

    private int buscaPosicao(Celula alvo) {
        // Função responsavel por procurar a posição de uma celula na lista
        // Proteção contra célula null
        if (alvo == null) {
            return -1;
        }
        
        Celula atual = primeiro;
        int pos = 0;
        boolean encontrado = false;
        // Quando a celula é encontrada, encerra a busca
        while (atual != null && !encontrado) {
            if (atual == alvo) {
                encontrado = true;
            } else {
                atual = atual.prox;
                pos++;
            }
        }
        return encontrado ? pos : -1;
    }

    private Celula selecionaPivo(Celula esq, Celula dir) {
        // Proteção contra células null
        if (esq == null || dir == null) {
            return esq;
        }
        
        // Busca a celula do meio;
        int posEsq = buscaPosicao(esq);
        int posDir = buscaPosicao(dir);
        
        // Proteção contra posições inválidas
        if (posEsq == -1 || posDir == -1) {
            return esq;
        }
        
        int posMeio = (posEsq + posDir) / 2;
        Celula meio = obterCelula(posMeio);
        
        // Proteção contra meio null
        if (meio == null) {
            return esq;
        }
        
        comps++;
        // Responsavel por dicidir qual elemento é a mediana, entre primeiro , meio e
        // ultimo
        if ((compareShows(esq.getShow(), meio.getShow()) >= 0 &&
                compareShows(esq.getShow(), dir.getShow()) <= 0) ||
                (compareShows(esq.getShow(), dir.getShow()) >= 0 &&
                        compareShows(esq.getShow(), meio.getShow()) <= 0)) {
            return esq;
        } else if ((compareShows(meio.getShow(), esq.getShow()) >= 0
                && compareShows(meio.getShow(), dir.getShow()) <= 0) ||
                (compareShows(meio.getShow(), dir.getShow()) >= 0
                        && compareShows(meio.getShow(), esq.getShow()) <= 0)) {
            return meio;
        } else {
            return dir;
        }
    }

    private Celula particionar(Celula esq, Celula dir) {
        // Função responsavel pelo particionamente do quickSort
        // O pivo é selecionado por mediana de 3;
        // Proteção contra células null
        if (esq == null || dir == null) {
            return esq;
        }
        
        Celula pivo = selecionaPivo(esq, dir);
        swap(pivo, dir);
        movs++;
        Show pivoShow = dir.getShow();
        Celula i = esq.ant;
        for (Celula j = esq; j != dir; j = j.prox) {
            comps++;
            if (compareShows(j.getShow(), pivoShow) <= 0) {
                i = (i == null) ? esq : i.prox;
                swap(i, j);
                movs++;
            }
        }
        i = (i == null) ? esq : i.prox;
        swap(i, dir);
        movs++;
        return i;
    }

    private void quickSort(Celula esq, Celula dir) {
        // Função quickSort recursiva
        // Proteção contra células null e casos base
        if (esq != null && dir != null && esq != dir && esq != dir.prox) {
            Celula pivo = particionar(esq, dir);
            if (pivo != null) {
                quickSort(esq, pivo.ant);
                quickSort(pivo.prox, dir);
            }
        }
    }

    public void ordernar() {
        // Função responsavel por chamar o quick sort
        if (primeiro != null && ultimo != null && tam > 1) {
            quickSort(primeiro, ultimo);
        }
    }

    public void imprimir() {
        for (Celula i = primeiro; i != null; i = i.prox) {
            if (i.getShow() != null) {
                i.getShow().imprimir();
            }
        }
    }

    public int getComps() {
        return comps;
    }

    public int getMovs() {
        return movs;
    }
}

public class Principal {

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = "";
        Lista lista = new Lista();
        Show show;
        try {
            // Primeira parte do codigo, responsavel por ler a primeira parte da entrada
            entrada = ler.nextLine();
            while (!entrada.equals("FIM")) {
                show = new Show(entrada);
                lista.inserir(show);
                entrada = ler.nextLine();
            }
            lista.ordernar();
            lista.imprimir();
            long fim = System.nanoTime();
            double tempoExec = (fim - inicio) / 1e6;
            
            // Proteção para criação do arquivo
            try (FileWriter arq = new FileWriter("matrícula_quicksort3.txt")) {
                arq.write("869899" + '\t' + lista.getComps() + '\t' + lista.getMovs() + '\t' + tempoExec);
            } catch (IOException e) {
                System.err.println("Erro ao escrever arquivo: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Erro durante execução: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ler.close();
        }
    }
}