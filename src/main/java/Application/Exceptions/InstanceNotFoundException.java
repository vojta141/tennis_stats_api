package Application.Exceptions;

public class InstanceNotFoundException extends Exception{
    public InstanceNotFoundException(){
        super("Instance with given ID not found");
    }
    public InstanceNotFoundException(String msg){
        super(msg);
    }
}
