package org.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Representa un coche.
 */
public class Coche {
  private int idCoche;
  private int anioFabricacion;
  private int kilometraje;
  private BigDecimal precioVenta;
  private String estado;
  private Timestamp fechaPublicacion;
  private String Imagen;
  private boolean activo;

  // Guardamos importes.
  private BigDecimal subtotal;
  private BigDecimal iva;
  private BigDecimal comision;
  private BigDecimal total;

  // Guardamos relaciones.
  private Version version;
  private TipoCombustible combustible;
  private TipoTransmision transmision;
  private Ciudad ciudad;
  private Color color;
  private EtiquetaAmbiental etiqueta;
  private Categoria categoria;
  private Usuario vendedor;

  /**
   * Instancia un nuevo Coche vacío.
   */
  public Coche() {
  }

  /**
   * Instancia un nuevo Coche con datos.
   *
   * @param idCoche          el ID del coche.
   * @param anioFabricacion  el año de fabricación.
   * @param kilometraje      el kilometraje.
   * @param precioVenta      el precio de venta.
   * @param estado           el estado del coche.
   * @param fechaPublicacion la fecha de publicación.
   * @param Imagen           la URL de la imagen.
   * @param version          la versión del coche.
   * @param combustible      el tipo de combustible.
   * @param transmision      el tipo de transmisión.
   * @param ciudad           la ciudad donde se encuentra.
   * @param color            el color del coche.
   * @param etiqueta         la etiqueta ambiental.
   * @param categoria        la categoría del coche.
   * @param vendedor         el usuario vendedor.
   * @param activo           indica si el coche está activo.
   */
  public Coche(int idCoche, int anioFabricacion, int kilometraje, BigDecimal precioVenta,
      String estado, Timestamp fechaPublicacion, String Imagen, Version version,
      TipoCombustible combustible, TipoTransmision transmision, Ciudad ciudad, Color color,
      EtiquetaAmbiental etiqueta, Categoria categoria, Usuario vendedor, boolean activo) {
    this.idCoche = idCoche;
    this.anioFabricacion = anioFabricacion;
    this.kilometraje = kilometraje;
    this.precioVenta = precioVenta;
    this.estado = estado;
    this.fechaPublicacion = fechaPublicacion;
    this.Imagen = Imagen;
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

  /**
   * Obtiene el ID del coche.
   *
   * @return el ID del coche.
   */
  public int getIdCoche() {
    return idCoche;
  }

  /**
   * Establece el ID del coche.
   *
   * @param idCoche el ID del coche.
   */
  public void setIdCoche(int idCoche) {
    this.idCoche = idCoche;
  }

  /**
   * Obtiene el año de fabricación.
   *
   * @return el año de fabricación.
   */
  public int getAnioFabricacion() {
    return anioFabricacion;
  }

  /**
   * Establece el año de fabricación.
   *
   * @param anioFabricacion el año de fabricación.
   */
  public void setAnioFabricacion(int anioFabricacion) {
    this.anioFabricacion = anioFabricacion;
  }

  /**
   * Obtiene el kilometraje.
   *
   * @return el kilometraje.
   */
  public int getKilometraje() {
    return kilometraje;
  }

  /**
   * Establece el kilometraje.
   *
   * @param kilometraje el kilometraje.
   */
  public void setKilometraje(int kilometraje) {
    this.kilometraje = kilometraje;
  }

  /**
   * Obtiene el precio de venta.
   *
   * @return el precio de venta.
   */
  public BigDecimal getPrecioVenta() {
    return precioVenta;
  }

  /**
   * Establece el precio de venta.
   *
   * @param precioVenta el precio de venta.
   */
  public void setPrecioVenta(BigDecimal precioVenta) {
    this.precioVenta = precioVenta;
  }

  /**
   * Obtiene el estado.
   *
   * @return el estado.
   */
  public String getEstado() {
    return estado;
  }

  /**
   * Establece el estado.
   *
   * @param estado el estado.
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * Obtiene la fecha de publicación.
   *
   * @return la fecha de publicación.
   */
  public Timestamp getFechaPublicacion() {
    return fechaPublicacion;
  }

  /**
   * Establece la fecha de publicación.
   *
   * @param fechaPublicacion la fecha de publicación.
   */
  public void setFechaPublicacion(Timestamp fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  /**
   * Obtiene la imagen.
   *
   * @return la imagen.
   */
  public String getImagen() {
    return Imagen;
  }

  /**
   * Establece la imagen.
   *
   * @param Imagen la imagen.
   */
  public void setImagen(String Imagen) {
    this.Imagen = Imagen;
  }

  /**
   * Obtiene la versión.
   *
   * @return la versión.
   */
  public Version getVersion() {
    return version;
  }

  /**
   * Establece la versión.
   *
   * @param version la versión.
   */
  public void setVersion(Version version) {
    this.version = version;
  }

  /**
   * Obtiene el tipo de combustible.
   *
   * @return el tipo de combustible.
   */
  public TipoCombustible getCombustible() {
    return combustible;
  }

  /**
   * Establece el tipo de combustible.
   *
   * @param combustible el tipo de combustible.
   */
  public void setCombustible(TipoCombustible combustible) {
    this.combustible = combustible;
  }

  /**
   * Obtiene el tipo de transmisión.
   *
   * @return el tipo de transmisión.
   */
  public TipoTransmision getTransmision() {
    return transmision;
  }

  /**
   * Establece el tipo de transmisión.
   *
   * @param transmision el tipo de transmisión.
   */
  public void setTransmision(TipoTransmision transmision) {
    this.transmision = transmision;
  }

  /**
   * Obtiene la ciudad.
   *
   * @return la ciudad.
   */
  public Ciudad getCiudad() {
    return ciudad;
  }

  /**
   * Establece la ciudad.
   *
   * @param ciudad la ciudad.
   */
  public void setCiudad(Ciudad ciudad) {
    this.ciudad = ciudad;
  }

  /**
   * Obtiene el color.
   *
   * @return el color.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Establece el color.
   *
   * @param color el color.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Obtiene la etiqueta ambiental.
   *
   * @return la etiqueta ambiental.
   */
  public EtiquetaAmbiental getEtiqueta() {
    return etiqueta;
  }

  /**
   * Establece la etiqueta ambiental.
   *
   * @param etiqueta la etiqueta ambiental.
   */
  public void setEtiqueta(EtiquetaAmbiental etiqueta) {
    this.etiqueta = etiqueta;
  }

  /**
   * Obtiene la categoría.
   *
   * @return la categoría.
   */
  public Categoria getCategoria() {
    return categoria;
  }

  /**
   * Establece la categoría.
   *
   * @param categoria la categoría.
   */
  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  /**
   * Obtiene el vendedor.
   *
   * @return el vendedor.
   */
  public Usuario getVendedor() {
    return vendedor;
  }

  /**
   * Establece el vendedor.
   *
   * @param vendedor el vendedor.
   */
  public void setVendedor(Usuario vendedor) {
    this.vendedor = vendedor;
  }

  /**
   * Verifica si está activo.
   *
   * @return true si está activo, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }

  /**
   * Obtiene el subtotal.
   *
   * @return el subtotal.
   */
  public BigDecimal getSubtotal() {
    return subtotal;
  }

  /**
   * Establece el subtotal.
   *
   * @param subtotal el subtotal.
   */
  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  /**
   * Obtiene el IVA.
   *
   * @return el IVA.
   */
  public BigDecimal getIva() {
    return iva;
  }

  /**
   * Establece el IVA.
   *
   * @param iva el IVA.
   */
  public void setIva(BigDecimal iva) {
    this.iva = iva;
  }

  /**
   * Obtiene la comisión.
   *
   * @return la comisión.
   */
  public BigDecimal getComision() {
    return comision;
  }

  /**
   * Establece la comisión.
   *
   * @param comision la comisión.
   */
  public void setComision(BigDecimal comision) {
    this.comision = comision;
  }

  /**
   * Obtiene el total.
   *
   * @return el total.
   */
  public BigDecimal getTotal() {
    return total;
  }

  /**
   * Establece el total.
   *
   * @param total el total.
   */
  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}