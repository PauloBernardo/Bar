/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bardaesquina;

import java.util.concurrent.Semaphore;
import javax.swing.JLabel;

/**
 *
 * @author paulo
 */
public class Cliente extends Thread {

    JLabel  label = null;
    String identificador = "Anônimo";
    float tempoCasa;
    float tempoBar;
    Semaphore s;
    Semaphore mutex;
    Semaphore mutex2;
    BarView bar;
    Cliente(
            JLabel get, 
            String identificador, 
            float tempoCasa, 
            float tempoBar, 
            Semaphore s, 
            Semaphore mutex,
            Semaphore mutex2,
            BarView father
    ) {
        super(identificador);
        this.label = get;
        this.identificador= identificador;
        this.tempoCasa = tempoCasa;
        this.tempoBar = tempoBar;
        this.s = s;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.bar = father;
    }
    
    
    public void printStatus(String status, float time) {
        long tempoInicial = System.currentTimeMillis();
        long before = (System.currentTimeMillis() - tempoInicial) / 1000;
        while ((System.currentTimeMillis() - tempoInicial) / 1000 < time) {
//            int count =  before !=(System.currentTimeMillis() - tempoInicial) / 1000? (int)(this.father.getjPanel2().getWidth()/time) : 0;
//            this.label.setLocation((this.label.getX() + count)%this.father.getjPanel2().getWidth(),  this.label.getY());
            this.label.setText(this.identificador + status + (
                        (System.currentTimeMillis() - tempoInicial) / 1000
                        ) + " - Tempo total:  " + time
                );
            before = (System.currentTimeMillis() - tempoInicial) / 1000;
        }
        this.label.setText(this.identificador + " na fila.");
    }
    
    @Override
    public void run() {
        String estadoAtual = " No Bar ";
        this.label.setText(this.identificador + " na fila.");
        while(true) {
            if(estadoAtual.equals(" No Bar ")) {
                    try {
                        this.mutex2.acquire();
                        System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " candidato -" + this.bar.numeroClientes);
                        this.mutex.acquire();
                                if(this.bar.numeroClientes < this.bar.qntCadeiras) {
                                    this.bar.numeroClientes++;
                                    this.mutex2.release();
                                }
                                 System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " apto -" + this.bar.numeroClientes);
                        this.mutex.release();
                       s.acquire();
                            System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " entrou -" + this.bar.numeroClientes);
                            this.printStatus(estadoAtual, this.tempoBar); // Beber
                            estadoAtual = " Em Casa ";
                            this.mutex.acquire();
                                this.bar.numeroClientes--;
                                System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " saiu -" + this.bar.numeroClientes);    
                                if(this.bar.numeroClientes == 0) this.mutex2.release();
                            this.mutex.release();
                        s.release();
                    } catch (Exception e) {
                            
                    }
            } else if (estadoAtual.equals(" Em Casa ")) {
                    this.printStatus(estadoAtual, this.tempoCasa);
                    estadoAtual = " No Bar ";
            }
        }
    }
    
}
