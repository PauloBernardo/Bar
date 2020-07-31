/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bardaesquina;

import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * @author paulo
 */
public class Cliente extends Thread {

    int clientIndex;
    JLabel clientImage;
    JLabel label;
    String identificador;
    float tempoCasa;
    float tempoBar;
    Semaphore controleDeVariaveis;
    Semaphore controleDeEntrada;
    BarView bar;
    String identificadorFormatado;
    int numCadeira = 0;

    Cliente(
            int index,
            JLabel clientImage,
            JLabel get,
            String identificador,
            float tempoCasa,
            float tempoBar,
            Semaphore mutex,
            Semaphore mutex2,
            BarView father
    ) {
        super(identificador);
        this.clientIndex = index;
        this.clientImage = clientImage;
        this.label = get;
        this.identificador = identificador;
        this.tempoCasa = tempoCasa;
        this.tempoBar = tempoBar;
        this.controleDeVariaveis = mutex;
        this.controleDeEntrada = mutex2;
        this.bar = father;
        this.identificadorFormatado = "[Bebum - " + identificador + "]";
    }

    private void setLocationAnimation(int numCadeira, String status) {
        switch (status) {
            case " No Bar ":

                if (this.clientImage.getY() == 70) {
                    break;
                }
                System.out.println("Inicio(" + this.clientImage.getX() + ", " + this.clientImage.getY() + ")");


                while (this.clientImage.getY() > 180) {
                    this.clientImage.setLocation(this.clientImage.getX(), this.clientImage.getY() - 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


                while (this.clientImage.getX() != ((numCadeira * 110) + 20)) {

                    if (this.clientImage.getX() > ((numCadeira * 110) + 20)) {
                        this.clientImage.setLocation(this.clientImage.getX() - 1, this.clientImage.getY());
                    } else {
                        this.clientImage.setLocation(this.clientImage.getX() + 1, this.clientImage.getY());

                    }

                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                System.out.println("Subindo para o Bar(" + this.clientImage.getX() + ", " + this.clientImage.getY() + ")");

                while (this.clientImage.getY() > 70) {
                    this.clientImage.setLocation(this.clientImage.getX(), this.clientImage.getY() - 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Chegou no Bar(" + this.clientImage.getX() + ", " + this.clientImage.getY() + ")");

                break;

            case " Em Casa ":
                int destinoX = (20 + this.clientIndex * 110);

                while (this.clientImage.getY() < 220) {
                    this.clientImage.setLocation(this.clientImage.getX(), this.clientImage.getY() + 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                while (this.clientImage.getX() != destinoX) {

                    if (this.clientImage.getX() > destinoX) {
                        this.clientImage.setLocation(this.clientImage.getX() - 1, this.clientImage.getY());
                    } else {
                        this.clientImage.setLocation(this.clientImage.getX() + 1, this.clientImage.getY());

                    }

                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


                while (this.clientImage.getY() < 290) {
                    this.clientImage.setLocation(20 + this.clientIndex * 110, this.clientImage.getY() + 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }


    private void printStatus(String status, float time, int cadeira) {
        if (status.equals(" No Bar ")) {
            this.label.setText(this.identificadorFormatado + " Indo para o Bar");
        } else {
            this.label.setText(this.identificadorFormatado + " Indo para Casa");
        }
        this.setLocationAnimation(cadeira - 1, status);
        this.label.setText(this.identificadorFormatado + status + "(Na fila)");

        long tempoInicial = System.currentTimeMillis();

        // PROCESSO CPU BOUND 
        while ((System.currentTimeMillis() - tempoInicial) / 1000.0 < time) {

            String tempoCorrido = String.format(Locale.US, "%.1f", ((float) (System.currentTimeMillis() - tempoInicial) / 1000));
            this.label.setText(this.identificadorFormatado + status + tempoCorrido + "/" + time);

        }
        this.label.setText(this.identificadorFormatado + status + "(Na fila)");
    }

    @Override
    public void run() {
        String estadoAtual = " No Bar ";
        this.label.setText(this.identificadorFormatado + " Em Casa(Na fila).");
        while (true) {
            if (estadoAtual.equals(" No Bar ")) {
                try {
                    // Dorme se tiver lotado
                    this.controleDeEntrada.acquire();

                    // MANIPULA AS VARIÁVEIS
                    this.controleDeVariaveis.acquire();

                        // Incrementa o número de clientes no bar
                        this.bar.numeroAtualDeClientes++;
                        System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " candidato - " + this.bar.numeroAtualDeClientes);

                        // Verifica se o bar está lotado
                        if (this.bar.numeroAtualDeClientes < this.bar.quantidadeTotalDeCadeiras) {
                            // Libera a entrada se não tiver lotado
                            this.controleDeEntrada.release();
                          
                        }
                        
                        // Seleciona o primeiro assento disponivel
                        for (int i = 0; i < this.bar.assentosDisponiveis.length; i++) {
                            if (this.bar.assentosDisponiveis[i] != -1) {
                                this.numCadeira = i + 1;
                                // Ocupa o assento
                                this.bar.assentosDisponiveis[i] = -1;
                                break;
                            }
                        }

                    this.controleDeVariaveis.release();
                    
                    // Bebe o tempo estipulado
                    this.printStatus(estadoAtual, this.tempoBar, this.numCadeira);

                    estadoAtual = " Em Casa ";
                    
                    // MANIPULA VARIAVEIS
                    this.controleDeVariaveis.acquire();
                        
                        // Sai do assento, deixando disponível
                        this.bar.assentosDisponiveis[this.numCadeira - 1] = 0;
                        // Diminui o número de clientes no bar
                        this.bar.numeroAtualDeClientes--;
                        // Libera a entrada se o bar estiver vazio
                        if (this.bar.numeroAtualDeClientes == 0) this.controleDeEntrada.release();

                    this.controleDeVariaveis.release();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                // Fica em casa o tempo estipulado
                this.printStatus(estadoAtual, this.tempoCasa, this.numCadeira);
                estadoAtual = " No Bar ";
            }
        }
    }

}
