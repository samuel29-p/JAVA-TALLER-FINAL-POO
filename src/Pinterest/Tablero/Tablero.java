package Pinterest.Tablero;

import Pinterest.Pins.Pin;
import Pinterest.Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tablero {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean esSecreto;
    private boolean esColaborativo;
    private Usuario propietario;
    private List<Pin> pins;
    private List<Usuario> colaboradores;

    public Tablero (int id, String nombre, String descripcion, Usuario propietario){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.propietario = propietario;
        this.pins = new ArrayList<>();
        this.colaboradores = new ArrayList<>();
        this.esSecreto = false;
        this.esColaborativo = false;
    }

    public void hacerSecreto(){
        this.esSecreto = true;
    }

    public void hacerColaborativo(){
        this.esColaborativo = true;
    }

    public void agregarPin(Pin pin){
        if (!pins.contains(pin)){
                pins.add(pin);
        }
    }

    public void eliminarPin(Pin pin){
        pins.remove(pin);
    }

    public void agregarColaborador(Usuario usuario){
        if(!colaboradores.contains(usuario)){
            colaboradores.add(usuario);
        }
    }

    public void eliminarColaborador(Usuario usuario){
        colaboradores.remove(usuario);
    }

    public int getId() {return id;}
    public String getNombre() {return nombre;} //para GestorBusqueda
    public Usuario getPropietario() {return propietario;} //para verificar permisos
    public List<Pin> getPins() {return pins;}//para GestorContenido
    public List<Usuario> getColaboradores() {return colaboradores;}//para verificar si alguien puede agregar pins
    public boolean isEsSecreto() {return esSecreto;} //solo getter de esSecreto para filtrar tableros publicos, si se necesita filtrar por tableros privados se agrega el otro getter

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tablero tablero = (Tablero) o;
        return id == tablero.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pinterest.Tablero.Tablero{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", esSecreto=" + esSecreto +
                ", esColaborativo=" + esColaborativo +
                ", propietario=" + propietario +
                ", pins=" + pins +
                ", colaboradores=" + colaboradores +
                '}';
    }
}
