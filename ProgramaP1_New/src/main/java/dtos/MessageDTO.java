/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author jc.ruiz
 */
public class MessageDTO {
    private String message;


    public MessageDTO() {

    }

    public MessageDTO(String message) {
        this.message = message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
}
