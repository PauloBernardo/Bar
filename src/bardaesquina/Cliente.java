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
    BarView father;
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
        this.father = father;
    }
    
    
    public void printStatus(String status, float time) {
        long tempoInicial = System.currentTimeMillis();
        while ((System.currentTimeMillis() - tempoInicial) / 1000 < time)
            this.label.setText(this.identificador + status + (
                        (System.currentTimeMillis() - tempoInicial) / 1000
                        )
                );
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
                        System.out.println("Cliente " + this.identificador + " candidato -" + this.father.empty);
                        this.mutex.acquire();
                                if(this.father.empty < this.father.qntCadeiras)
                                    this.father.empty++;
                                if(this.father.empty < this.father.qntCadeiras) {
                                        this.mutex2.release();
                                }
                                 System.out.println("Cliente " + this.identificador + " apto -" + this.father.empty);
                        this.mutex.release();
                       s.acquire();
                            System.out.println("Cliente " + this.identificador + " entrou -" + this.father.empty);
                            this.printStatus(estadoAtual, this.tempoBar);
                            estadoAtual = " Em Casa ";
                            this.mutex.acquire();
                            this.father.empty--;
                            if(this.father.empty == 0) this.mutex2.release();
                            System.out.println("Cliente " + this.identificador + " saiu -" + this.father.empty);
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
