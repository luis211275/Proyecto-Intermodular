package intermodular.model;

/**
 * Clase que representa la entidad Coche en la base de datos.
 * Se utiliza principalmente para recibir datos del frontend (POST/PUT)
 * y para la persistencia en la base de datos.
 */
public class Coches {
  private int idCoche;
  private int anioFabricacion;
  private int kilometraje;
  private double precioVenta;
  private Boolean esPremium;
  private String estado;
  private String fechaPublicacion;
  private int versionId;
  private int combustibleId;
  private int transmisionId;
  private int ciudadId;
  private int colorId;
  private int etiquetaId;
  private int categoriaId;
  private int vendedorId;

  public Coches() {
  }

  // Constructor simplificado para creación básica
  public Coches(int anioFabricacion, int kilometraje, double precioVenta, int versionId, int vendedorId) {
    this.anioFabricacion = anioFabricacion;
    this.kilometraje = kilometraje;
    this.precioVenta = precioVenta;
    this.versionId = versionId;
    this.vendedorId = vendedorId;
  }

  // Getters y Setters
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

  public double getPrecioVenta() {
    return precioVenta;
  }

  public void setPrecioVenta(double precioVenta) {
    this.precioVenta = precioVenta;
  }

  public Boolean getEsPremium() {
    return esPremium;
  }

  public void setEsPremium(Boolean esPremium) {
    this.esPremium = esPremium;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(String fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public int getVersionId() {
    return versionId;
  }

  public void setVersionId(int versionId) {
    this.versionId = versionId;
  }

  public int getCombustibleId() {
    return combustibleId;
  }

  public void setCombustibleId(int combustibleId) {
    this.combustibleId = combustibleId;
  }

  public int getTransmisionId() {
    return transmisionId;
  }

  public void setTransmisionId(int transmisionId) {
    this.transmisionId = transmisionId;
  }

  public int getCiudadId() {
    return ciudadId;
  }

  public void setCiudadId(int ciudadId) {
    this.ciudadId = ciudadId;
  }

  public int getColorId() {
    return colorId;
  }

  public void setColorId(int colorId) {
    this.colorId = colorId;
  }

  public int getEtiquetaId() {
    return etiquetaId;
  }

  public void setEtiquetaId(int etiquetaId) {
    this.etiquetaId = etiquetaId;
  }

  public int getCategoriaId() {
    return categoriaId;
  }

  public void setCategoriaId(int categoriaId) {
    this.categoriaId = categoriaId;
  }

  public int getVendedorId() {
    return vendedorId;
  }

  public void setVendedorId(int vendedorId) {
    this.vendedorId = vendedorId;
  }

  @Override
  public String toString() {
    return "Coches{" +
        "idCoche=" + idCoche +
        ", anioFabricacion=" + anioFabricacion +
        ", precioVenta=" + precioVenta +
        ", estado='" + estado + '\'' +
        '}';
  }
}