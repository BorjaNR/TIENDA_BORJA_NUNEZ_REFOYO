package es.serbatic.modelo.hilos;

import es.serbatic.modelo.DAO.PedidoDAO;
import es.serbatic.modelo.VO.PedidoVO;

public class CambioEstadoDetalle implements Runnable {
    private int id;
    private PedidoDAO dao;

    // Constructor que recibe los dos parámetros
    public CambioEstadoDetalle(int id, PedidoDAO dao) {
        this.id = id;
        this.dao = dao;
    }

    @Override
    public void run() {
        try {
            // Simula un retraso de 45 segundos (45000 ms)
            Thread.sleep(45000);
            PedidoVO d = dao.findById(id).orElse(null);
            if(d.getEstado().equals("Pendiente de envio")) {
                d.setEstado("Enviado");
                dao.save(d);
            }  
        } catch (InterruptedException e) {
            System.err.println("El hilo fue interrumpido: " + e.getMessage());
        }
    }
}