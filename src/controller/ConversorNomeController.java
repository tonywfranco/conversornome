package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import model.Palavras;
import java.util.Comparator;
import javafx.scene.text.Text;

/**
 *
 * @author Tony
 */
public class ConversorNomeController implements Initializable, Comparator<Palavras> {

    @FXML
    private TextArea txTexto;

    @FXML
    private TextArea txTextoConv;

    @FXML
    private Button btLimpar;

    @FXML
    private TextArea txHash1;

    @FXML
    private TextArea txHash2;

    @FXML
    private Button btLimpar2;

    @FXML
    private TableView<Palavras> tbPalavras;

    @FXML
    private TableColumn<Palavras, Boolean> clCheck;

    @FXML
    private TableColumn<Palavras, String> clPalavras;

    @FXML
    private Button btCopiar;

    @FXML
    private CheckBox chkTodos;

    @FXML
    private Text txSelecionados;

    ArrayList<Palavras> palavras = new ArrayList<>();
    ArrayList<Palavras> palavras2 = new ArrayList<>();
    ArrayList<Palavras> palavras3 = new ArrayList<>();
    int selecionados;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txTexto.setWrapText(true);
        txTextoConv.setWrapText(true);
        txHash1.setWrapText(true);
        txHash2.setWrapText(true);
        chkTodos.setAlignment(Pos.CENTER);

        txTexto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                converteTexto();
            }
        });

        txTextoConv.setOnMouseClicked((event) -> {
            String copyText = txTextoConv.getText();
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(copyText);
            clipboard.setContent(content);
        });

        btLimpar.setOnAction((event) -> {
            txTexto.setText("");
            txTexto.requestFocus();
        });

        txHash1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                selecionados = 0;
                convertePalavras();
                chkTodos.setSelected(false);
                if (txHash1.getText().isEmpty()) {
                    initTable();
                    txHash2.setText("");
                }
            }
        });

        txHash2.setOnMouseClicked((event) -> {
            String copyText = txHash2.getText();
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(copyText);
            clipboard.setContent(content);
        });

        btLimpar2.setOnAction((event) -> {
            txHash1.setText("");
            txHash1.requestFocus();
        });

        btCopiar.setOnAction((event) -> {
            ArrayList<Palavras> palsel = new ArrayList<>();
            for (int i = 0; i < palavras2.size(); i++) {
                if (palavras2.get(i).getSelect().isSelected()) {
                    palsel.add(palavras2.get(i));
                }
            }
            String prepara = "";
            for (int i = 0; i < palsel.size(); i++) {
                prepara = prepara.concat(palsel.get(i).getPalavra());
            }

            palsel.clear();

            String a = prepara;
            a = a.replaceAll(" ", "");
            a = a.replaceAll("#", " #");
            try {
                a = a.substring(1, a.length());
            } catch (Exception e) {
            }

            String copyText = a;
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(copyText);
            clipboard.setContent(content);
        });

        chkTodos.selectedProperty().addListener((observable, oldValue, newValue) -> {
            for (Palavras item : tbPalavras.getItems()) {
                item.setCheck(newValue);
            }
        });

    }

    private void convertePalavras() {
        String a = txHash1.getText();
        a = a.replaceAll(" ", "");
        a = a.replaceAll(",", "#");
        a = a.replaceAll("#", " #");
        String b = "#";
        txHash2.setText(b.concat(a));
        preparaTabela(txHash2.getText());
    }

    private void preparaTabela(String a) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '#') {
                count = count + 1;
            }
        }

        for (int i = 0; i < count; i++) {
            int c = a.indexOf("#");
            int e = a.indexOf(" ");
            if (i + 1 == count) {
                Palavras p = new Palavras();
                p.setSelect(new CheckBox());
                p.setPalavra(a);
                palavras.add(p);
            } else {
                String d = a.substring(c, e + 1);
                Palavras p = new Palavras();
                p.setSelect(new CheckBox());
                p.setPalavra(d);
                palavras.add(p);
            }
            a = a.substring(e + 1, a.length());
        }
        ConversorNomeController conversor = new ConversorNomeController();
        Collections.sort(palavras, conversor);
        initTable();
        palavras2.clear();
        palavras3.clear();
        for (int i = 0; i < palavras.size(); i++) {
            palavras2.add(palavras.get(i));
            palavras3.add(palavras2.get(i));
        }
        palavras.clear();
    }

    private void initTable() {
        clCheck.setCellValueFactory(new PropertyValueFactory<>("select"));
        clPalavras.setCellValueFactory(new PropertyValueFactory<>("palavra"));
        tbPalavras.setItems(atualizarTabela());
    }

    private ObservableList<Palavras> atualizarTabela() {
        return FXCollections.observableArrayList(palavras);
    }

    private void converteTexto() {
        String txt = txTexto.getText();
        String newtxt = txt.replaceAll(" ", "_");
        String newtxt2 = newtxt.replaceAll("ã", "a");
        String newtxt3 = newtxt2.replaceAll("á", "a");
        String newtxt4 = newtxt3.replaceAll("à", "a");
        String newtxt5 = newtxt4.replaceAll("é", "e");
        String newtxt6 = newtxt5.replaceAll("è", "e");
        String newtxt7 = newtxt6.replaceAll("í", "i");
        String newtxt8 = newtxt7.replaceAll("ì", "i");
        String newtxt9 = newtxt8.replaceAll("ó", "o");
        String newtxt10 = newtxt9.replaceAll("ò", "o");
        String newtxt11 = newtxt10.replaceAll("ú", "u");
        String newtxt12 = newtxt11.replaceAll("ù", "u");
        String newtxt13 = newtxt12.replaceAll("ç", "c");
        String newtxt14 = newtxt13.replaceAll("-", "_");
        String newtxt15 = newtxt14.replaceAll("~", "_");
        String newtxt16 = newtxt15.replaceAll("Á", "a");
        String newtxt17 = newtxt16.replaceAll("À", "a");
        String newtxt18 = newtxt17.replaceAll("É", "e");
        String newtxt19 = newtxt18.replaceAll("È", "e");
        String newtxt20 = newtxt19.replaceAll("Í", "i");
        String newtxt21 = newtxt20.replaceAll("Ì", "i");
        String newtxt22 = newtxt21.replaceAll("Ó", "o");
        String newtxt23 = newtxt22.replaceAll("Ò", "o");
        String newtxt24 = newtxt23.replaceAll("Ú", "u");
        String newtxt25 = newtxt24.replaceAll("Ù", "u");
        String newtxt26 = newtxt25.replaceAll("&", "_");
        String newtxt27 = newtxt26.replaceAll("@", "_");
        String newtxt28 = newtxt27.replaceAll("#", "_");
        String newtxt29 = newtxt28.replaceAll("%", "_");
        String newtxt30 = newtxt29.replaceAll("=", "_");
        String newtxt31 = newtxt30.replaceAll("!", "_");
        String newtxt32 = newtxt31.replaceAll("¨", "_");
        String newtxt33 = newtxt32.replaceAll("´", "_");
        String newtxt34 = newtxt33.replaceAll("/", "_");
        String newtxt35 = newtxt34.replaceAll(":", "_");
        String newtxt36 = newtxt35.replaceAll(";", "_");
        String newtxt37 = newtxt36.replaceAll("]", "_");
        String newtxt38 = newtxt37.replaceAll("}", "_");
        String newtxt39 = newtxt38.replaceAll("<", "_");
        String newtxt40 = newtxt39.replaceAll(">", "_");
        String newtxt41 = newtxt40.replaceAll("'", "_");
        String newtxt42 = newtxt41.replaceAll("º", "_");
        String newtxt43 = newtxt42.replaceAll("ª", "_");
        String newtxt44 = newtxt43.replaceAll(",", "_");
        String newtxt45 = newtxt44.replaceAll("ô", "o");
        String newtxt46 = newtxt45.replaceAll("Ô", "o");
        String newtxt47 = newtxt46.replaceAll("â", "a");
        String newtxt48 = newtxt47.replaceAll("Â", "a");
        String newtxt49 = newtxt48.replaceAll("ê", "e");
        String newtxt50 = newtxt49.replaceAll("Ê", "e");
        String newtxt51 = newtxt50.replaceAll("î", "i");
        String newtxt52 = newtxt51.replaceAll("Î", "i");
        String newtxt53 = newtxt52.replaceAll("û", "u");
        String newtxt54 = newtxt53.replaceAll("Û", "u");
        String newtxt55 = newtxt54.replaceAll("Õ", "o");
        String newtxt56 = newtxt55.replaceAll("õ", "o");
        String newtxt57 = newtxt56.replaceAll("Ã", "a");
        String newtxt58 = newtxt57.replaceAll("__", "_");
        String newtxt59 = newtxt58.replaceAll("\\(", "");
        String newtxt60 = newtxt59.replaceAll("\\)", "");
        String newtxt61 = newtxt60.replaceAll("\\*", "");
        String newtxt62 = newtxt61.replaceAll("\\.", "");
        String newtxt63 = newtxt62.replaceAll("́", "");
        String newtxt64 = newtxt63.replaceAll("̀", "");
        String newtxt65 = newtxt64.replaceAll("̧", "");
        String newtxt66 = newtxt65.replaceAll("̃", "");
        String newtxt67 = newtxt66.replaceAll("“", "");
        String newtxt68 = newtxt67.replaceAll("”", "");
        String newtxt69 = newtxt68.replaceAll("-", "");
        String newtxt70 = newtxt69.replaceAll("__", "_");

        txTextoConv.setText(newtxt70);
    }

    @Override
    public int compare(Palavras o1, Palavras o2) {
        return o1.getPalavra().toLowerCase().compareTo(o2.getPalavra().toLowerCase());
    }

}
