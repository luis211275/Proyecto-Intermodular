package intermodular.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Coche {
  private int idCoche;
  private int anioFabricacion;
  private int kilometraje;
  private BigDecimal precioVenta;
  private String estado;
  private Timestamp fechaPublicacion;
  private String urlImagen;
  private boolean activo;
  
  // Cálculos para la compraventa
  private BigDecimal subtotal;
  private BigDecimal iva;
  private BigDecimal comision;
  private BigDecimal total;

  // Composición
  private Version version;
  private TipoCombustible combustible;
  private TipoTransmision transmision;
  private Ciudad ciudad;
  private Color color;
  private EtiquetaAmbiental etiqueta;
  private Categoria categoria;
  private Usuario vendedor;

  public Coche() {
  }

  public Coche(int idCoche, int anioFabricacion, int kilometraje, BigDecimal precioVenta,
               String estado, Timestamp fechaPublicacion, String urlImagen, Version version,
               TipoCombustible combustible, TipoTransmision transmision, Ciudad ciudad, Color color,
               EtiquetaAmbiental etiqueta, Categoria categoria, Usuario vendedor, boolean activo) {
    this.idCoche = idCoche;
    this.anioFabricacion = anioFabricacion;
    this.kilometraje = kilometraje;
    this.precioVenta = precioVenta;
    this.estado = estado;
    this.fechaPublicacion = fechaPublicacion;
    this.urlImagen = urlImagen;
    this.version = version;
    this.combustible = combustible;
    this.transmision = transmision;
    this.ciudad = ciudad;
    this.color = color;
    this.etiqueta = etiqueta;
    this.categoria = categoria;
    this.vendedor = vendedor;
    this.activo = activo;
  }

  public int getIdCoche() {
    return idCoche;
  }

  public void setIdCoche(int idCoche) {
    this.idCoche = idCoche;
  }

  public int getAnioFabricacion() {
    return anioFabricacion;
  }

  public void setAnioFabricacion(int anioFabricacion) {
    this.anioFabricacion = anioFabricacion;
  }

  public int getKilometraje() {
    return kilometraje;
  }

  public void setKilometraje(int kilometraje) {
    this.kilometraje = kilometraje;
  }

  public BigDecimal getPrecioVenta() {
    return precioVenta;
  }

  public void setPrecioVenta(BigDecimal precioVenta) {
    this.precioVenta = precioVenta;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Timestamp getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(Timestamp fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public String getUrlImagen() {
    return urlImagen;
  }

  public void setUrlImagen(String urlImagen) {
    this.urlImagen = urlImagen;
  }

  public Version getVersion() {
    return version;
  }

  public void setVersion(Version version) {
    this.version = version;
  }

  public TipoCombustible getCombustible() {
    return combustible;
  }

  public void setCombustible(TipoCombustible combustible) {
    this.combustible = combustible;
  }

  public TipoTransmision getTransmision() {
    return transmision;
  }

  public void setTransmision(TipoTransmision transmision) {
    this.transmision = transmision;
  }

  public Ciudad getCiudad() {
    return ciudad;
  }

  public void setCiudad(Ciudad ciudad) {
    this.ciudad = ciudad;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public EtiquetaAmbiental getEtiqueta() {
    return etiqueta;
  }

  public void setEtiqueta(EtiquetaAmbiental etiqueta) {
    this.etiqueta = etiqueta;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Usuario getVendedor() {
    return vendedor;
  }

  public void setVendedor(Usuario vendedor) {
    this.vendedor = vendedor;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  public BigDecimal getIva() {
    return iva;
  }

  public void setIva(BigDecimal iva) {
    this.iva = iva;
  }

  public BigDecimal getComision() {
    return comision;
  }

  public void setComision(BigDecimal comision) {
    this.comision = comision;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}
