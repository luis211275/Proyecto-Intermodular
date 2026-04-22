package intermodular.model;

public class LineaFactura {
    private int id;
    private int facturaId;
    private int cocheId;
    private double precioVentaMomento;

    public LineaFactura() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFacturaId() { return facturaId; }
    public void setFacturaId(int facturaId) { this.facturaId = facturaId; }

    public int getCocheId() { return cocheId; }
    public void setCocheId(int cocheId) { this.cocheId = cocheId; }

    public double getPrecioVentaMomento() { return precioVentaMomento; }
    public void setPrecioVentaMomento(double precioVentaMomento) { this.precioVentaMomento = precioVentaMomento; }
}
