package model;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Tony
 */
public class Palavras {

    String palavra;
    CheckBox select;

    public Palavras() {
    }

    public Palavras(String palavra, CheckBox select) {
        this.palavra = palavra;
        this.select = select;
    }

    public Palavras(String palavra) {
        this.palavra = palavra;
    }

    public Palavras(CheckBox select) {
        this.select = select;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
    public void setCheck(Boolean x) {
        if (x) {
            select.setSelected(true);
        } else {
            select.setSelected(false);
        }
    }

    @Override
    public String toString() {
        return "Palavras{" + "palavra=" + palavra + ", select=" + select + '}';
    }
    
    

}
