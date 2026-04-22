package intermodular.model;

import java.util.Date;

public class Factura {
    private int id;
    private int compradorId;
    private double totalBase;
    private double ivaImporte;
    private double comisionPlataforma;
    private double totalPagado;
    private Date fechaEmision;

    public Factura() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCompradorId() { return compradorId; }
    public void setCompradorId(int compradorId) { this.compradorId = compradorId; }

    public double getTotalBase() { return totalBase; }
    public void setTotalBase(double totalBase) { this.totalBase = totalBase; }

    public double getIvaImporte() { return ivaImporte; }
    public void setIvaImporte(double ivaImporte) { this.ivaImporte = ivaImporte; }

    public double getComisionPlataforma() { return comisionPlataforma; }
    public void setComisionPlataforma(double comisionPlataforma) { this.comisionPlataforma = comisionPlataforma; }

    public double getTotalPagado() { return totalPagado; }
    public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }

    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }
}
