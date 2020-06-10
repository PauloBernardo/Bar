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
    
    int clientIndex = 0;
    JLabel clientImage = null;
    JLabel label = null;
    String identificador = "Anônimo";
    float tempoCasa;
    float tempoBar;
    Semaphore s;
    Semaphore mutex;
    Semaphore mutex2;
    BarView bar;
    String identificadorFormatado;
    
    Cliente(
            int index,
            JLabel clientImage,
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
        this.clientIndex = index;
        this.clientImage = clientImage;
        this.label = get;
        this.identificador = identificador;
        this.tempoCasa = tempoCasa;
        this.tempoBar = tempoBar;
        this.s = s;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.bar = father;
        this.identificadorFormatado = "[Bebum - " + identificador  +"]";
    }
    
    private void setLocationAnimation(int numCadeira, String status) {
        switch(status) {
            case " No Bar ":
                
                if(this.clientImage.getY() == 70){
                    break;
                }
                System.out.println("Inicio(" + this.clientImage.getX()+", "+this.clientImage.getY()+")");
                

                while(this.clientImage.getY() > 180) {
                    this.clientImage.setLocation(this.clientImage.getX(), this.clientImage.getY() - 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                while(this.clientImage.getX() != ((numCadeira * 110) +20)) {
                    
                    if(this.clientImage.getX() > ((numCadeira * 110) +20)){
                        this.clientImage.setLocation(this.clientImage.getX()-1, this.clientImage.getY());
                    }else{
                        this.clientImage.setLocation(this.clientImage.getX()+1, this.clientImage.getY());
                        
                    }
                    
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                System.out.println("Subindo para o Bar(" + this.clientImage.getX()+", "+this.clientImage.getY()+")");

                while(this.clientImage.getY() > 70) {
                    this.clientImage.setLocation(this.clientImage.getX(), this.clientImage.getY() - 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Chegou no Bar(" + this.clientImage.getX()+", "+this.clientImage.getY()+")");

                break;
                
            case " Em Casa ":
                int destinoX = (20 + this.clientIndex * 110);
                
                while(this.clientImage.getY() < 220) {
                    this.clientImage.setLocation(this.clientImage.getX(), this.clientImage.getY() + 1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                                
                while(this.clientImage.getX() != destinoX) {
                    
                    if(this.clientImage.getX() > destinoX){
                        this.clientImage.setLocation(this.clientImage.getX()-1, this.clientImage.getY());
                    }else{
                        this.clientImage.setLocation(this.clientImage.getX()+1, this.clientImage.getY());
                        
                    }
                    
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                while(this.clientImage.getY() < 290) {
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
    
    
    
    private void printStatus(String status, float time,int cadeira) {
        if(status.equals(" No Bar ")){
                this.label.setText(this.identificadorFormatado + " Indo para o Bar");

        }else{
            this.label.setText(this.identificadorFormatado + " Indo para Casa");
            
        }
        this.setLocationAnimation(cadeira-1, status);
        this.label.setText(this.identificadorFormatado + status + "(Na fila)");

        long tempoInicial = System.currentTimeMillis();
        
        while ((System.currentTimeMillis() - tempoInicial) / 1000.0 < time) {
            
            String tempoCorrido = String.format(Locale.US,"%.1f",((float)(System.currentTimeMillis() - tempoInicial) / 1000));
            this.label.setText(this.identificadorFormatado + status + tempoCorrido + "/" + time );
            
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
                    
                    // ---------------- MUTEX 2 ------------------
                    this.mutex2.acquire();
                    
                    System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " candidato -" + this.bar.numeroClientes);
                    
                    // ---------------- MUTEX 1 ------------------
                    this.mutex.acquire();
                    this.bar.numeroClientes++;
                    if (this.bar.numeroClientes < this.bar.qntCadeiras) {
                        this.mutex2.release();
                        // ---------------- MUTEX 2 ------------------
                        
                    }
                    //System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " apto -" + this.bar.numeroClientes);
                   
                    int numCadeira = this.bar.numeroClientes;

                    this.mutex.release();
                    this.printStatus(estadoAtual, this.tempoBar,numCadeira); // Beber

                    // ---------------- MUTEX 1 ------------------
                    
                    // 
                    s.acquire();
                    //System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " entrou -" + this.bar.numeroClientes);
                    estadoAtual = " Em Casa ";
                    this.mutex.acquire();
                    this.bar.numeroClientes--;
                    //System.out.println(System.currentTimeMillis() + " -> Cliente " + this.identificador + " saiu -" + this.bar.numeroClientes);
                    
                    if (this.bar.numeroClientes == 0) this.mutex2.release();
                    // ---------------- MUTEX 2 ------------------

                    this.mutex.release();
                    s.release();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    this.mutex.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }

                    int numCadeira = this.bar.numeroClientes;
                    this.mutex.release();

                this.printStatus(estadoAtual, this.tempoCasa,numCadeira);

                estadoAtual = " No Bar ";
            }
        }
    }
    
}
