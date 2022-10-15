package ca.sfu.cmpt213.Assignment5;

import ca.sfu.cmpt213.Assignment5.entity.HttpClientUtils;
import ca.sfu.cmpt213.Assignment5.entity.HttpResponse;
import ca.sfu.cmpt213.Assignment5.entity.Tokimon;
import com.alibaba.fastjson.JSON;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class Controller {
    @FXML
    private TableView<Tokimon> tableView;

    @FXML
    private TableColumn<Tokimon, String> idColumn;

    @FXML
    private TableColumn<Tokimon, String> nameColumn;

    @FXML
    private TableColumn<Tokimon, String> weightColumn;

    @FXML
    private TableColumn<Tokimon, String> heightColumn;

    @FXML
    private TableColumn<Tokimon, String> abilityColumn;
    @FXML
    private TableColumn<Tokimon, String> strengthColumn;
    @FXML
    private TableColumn<Tokimon, String> colourColumn;
    @FXML
    private TableColumn<Tokimon, Boolean> selectedColumn;


    @FXML
    private TextField idAdd;

    @FXML
    private TextField nameAdd;

    @FXML
    private TextField weightAdd;

    @FXML
    private TextField heightAdd;

    @FXML
    private TextField abilityAdd;

    @FXML
    private TextField strengthAdd;

    @FXML
    private TextField colourAdd;

    @FXML
    private TextField idModify;

    @FXML
    private TextField nameModify;

    @FXML
    private TextField weightModify;

    @FXML
    private TextField heightModify;

    @FXML
    private TextField abilityModify;

    @FXML
    private TextField strengthModify;

    @FXML
    private TextField colourModify;

    @FXML
    private TextField idSearch;

    @FXML
    private TextField nameSearch;

    private boolean index = false;

    HttpClientUtils httpClientUtils = new HttpClientUtils();

    //the table content list
    private final ObservableList<Tokimon> data = FXCollections.observableArrayList();


    //list the data list that also contain the table content
    private final List<Tokimon> tokimonList = new ArrayList<>();
    private final List<Tokimon> tmp = new ArrayList<>();

    /**
     *
     * @ useschange the byte array
     * @param tokimonList
     * @return
     */
    public byte[] listToBytes(List<Tokimon> tokimonList) {
        StringBuffer sb = new StringBuffer();
        for (Tokimon tokimon : tokimonList) {
            sb.append(tokimon.toString());
        }
        return sb.toString().getBytes();
    }

    /**
     * @add
     * @param event
     * @throws IOException
     */
    @FXML
    void add(ActionEvent event) throws IOException {
        if (idAdd.getText() != null
                && nameAdd.getText() != null
                && weightAdd.getText() != null
                && heightAdd.getText() != null
                && abilityAdd.getText() != null
                && strengthAdd.getText() != null
                && colourAdd.getText() != null) {
            //to get the input
            Tokimon tokimon = new Tokimon(
                    idAdd.getText(),
                    nameAdd.getText(),
                    weightAdd.getText(),
                    heightAdd.getText(),
                    abilityAdd.getText(),
                    strengthAdd.getText(),
                    colourAdd.getText());
            HttpResponse response  = httpClientUtils.httpGet("http://127.0.0.1:9000/hw/add" +
                    "?id="+ tokimon.getId()+
                    "&name="+ tokimon.getName()+
                    "&weight="+ tokimon.getWeight()+
                    "&height="+ tokimon.getHeight()+
                    "&ability="+ tokimon.getAbility()+
                    "&strength="+ tokimon.getStrength()+
                    "&colour="+ tokimon.getColour(),new ArrayList(),null);

            //add into the table
            data.add(tokimon);
            //add into the list
            tokimonList.add(tokimon);

            System.out.println("[add]: " +
                    idAdd.getText() + " "
                    + nameAdd.getText() + " "
                    + weightAdd.getText() + " "
                    + heightAdd.getText() + " "
                    + abilityAdd.getText() + " "
                    + strengthAdd.getText() + " "
                    + strengthAdd.getText() + " "
            );


            //just clear the enter space
            idAdd.clear();
            nameAdd.clear();
            weightAdd.clear();
            heightAdd.clear();
            abilityAdd.clear();
            strengthAdd.clear();
            colourAdd.clear();

        }
    }

    /**
     * @delete
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void delete(ActionEvent event) throws IOException {
        deleteStudents();
    }

    private boolean deleteStudents() throws IOException {
        int size = data.size();
        if (size <= 0) {
            return false;
        }
        //to go through all the items
        StringJoiner joiner = new StringJoiner(",", "", "");
        for (int i = size - 1; i >= 0; i--) {
            Tokimon p = data.get(i);
            if (p.isSelected()) {
                joiner.add(p.getId());
                //remove from the list
                tokimonList.remove(p);
//                //remove from the table
                data.remove(p);
            }
        }
        HttpResponse response  = httpClientUtils.httpGet("http://127.0.0.1:9000/hw/delete?ids="+joiner,new ArrayList<>(),null);
        return true;
    }

    /**
     * modify
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void modify(ActionEvent event) throws IOException {
        boolean flag = false;
        if (idModify.getText() != null
                && nameModify.getText() != null
                && weightModify.getText() != null
                && heightModify.getText() != null
                && abilityModify.getText() != null
                && strengthModify.getText() != null
                && colourModify.getText() != null
                ) {

            //go through the list，check if need to change the id as well
            for (Tokimon p : tokimonList) {
                if (p.getId().equals(idModify.getText())) {
                    flag = true;
                    //remove all data
                    data.removeAll(tokimonList);
                    //update the data
                    p.setId(idModify.getText());
                    p.setName(nameModify.getText());
                    p.setWeight(weightModify.getText());
                    p.setHeight(heightModify.getText());
                    p.setAbility(abilityModify.getText());
                    p.setStrength(strengthModify.getText());
                    p.setColour(colourModify.getText());

                    HttpResponse response  = httpClientUtils.httpGet("http://127.0.0.1:9000/hw/modify" +
                            "?id="+p.getId()+
                            "&name="+p.getName()+
                            "&weight="+p.getWeight()+
                            "&height="+p.getHeight()+
                            "&ability="+p.getAbility()+
                            "&strength="+p.getStrength()+
                            "&colour="+p.getColour(),new ArrayList(),null);


                    System.out.println("[modify]: " + p.getId() + " " + p.getName() + " " + p.getWeight() + " " + p.getWeight()+"");
                }
            }
            if (flag) {
                data.addAll(tokimonList);
            }
            idModify.clear();
            nameModify.clear();
            weightModify.clear();
            heightModify.clear();
            abilityModify.clear();
            strengthModify.clear();
            colourModify.clear();

        }
    }

    /**
     * @search
     * @only work for name and id
     * @param event
     * @throws CloneNotSupportedException
     */
    @FXML
    void search(ActionEvent event) throws CloneNotSupportedException {
        for (Tokimon p : tokimonList) {
            tmp.add(p.clone());
        }
        data.removeAll(tokimonList);

        String id = idSearch.getText();
        String name = nameSearch.getText();
        if (id.equals("") && name.equals("")) {
            data.addAll(tokimonList);
        }

        List<Header> headersList =new ArrayList<>();
        Header header = new BasicHeader("id", id);
        Header header2 = new BasicHeader("name", name);
        headersList.add(header2);
        headersList.add(header);
        HttpResponse response  = httpClientUtils.httpGet("http://127.0.0.1:9000/hw/select?id="+id+"&name="+name,headersList,null);
        List<String> lines =JSON.parseArray( response.getBody().toString(),String.class);
        //add into fx table
        for (String line : lines) {
            String[] tmp = line.split(",");
            tokimonList.add(new Tokimon(tmp[0], tmp[1], tmp[2],tmp[3],tmp[4],tmp[5],tmp[6]));
            data.add(tokimonList.get(tokimonList.size() - 1));
        }
        idSearch.clear();
        nameSearch.clear();
    }

    @FXML
    private void initialize() throws IOException {
        idColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("id"));
        selectedColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, Boolean>("selected"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("name"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("height"));
        abilityColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("ability"));
        strengthColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("strength"));
        colourColumn.setCellValueFactory(new PropertyValueFactory<Tokimon, String>("colour"));
        tableView.setItems(data);
        tableView.setEditable(true);

        HttpResponse response  = httpClientUtils.httpGet("http://127.0.0.1:9000/hw/select",new ArrayList(),null);

        List<String> lines =JSON.parseArray( response.getBody().toString(),String.class);
        for (String line : lines) {
            String[] tmp = line.split(",");
            tokimonList.add(new Tokimon(tmp[0], tmp[1], tmp[2],tmp[3],tmp[4],tmp[5],tmp[6]));
            data.add(tokimonList.get(tokimonList.size() - 1));
        }

        // work on the cell selection
        selectedColumn.setCellFactory(
                CellFactory.tableCheckBoxColumn(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        final Tokimon tokimon = tableView.getItems().get(index);
                        ObservableValue<Boolean> ret =
                                new SimpleBooleanProperty(tokimon, "selected", tokimon.isSelected());
                        ret.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends Boolean> observable,
                                    Boolean oldValue, Boolean newValue) {
                                tokimon.setSelected(newValue);
                            }
                        });
                        return ret;
                    }
                }));

        Callback<TableColumn<Tokimon, String>,
                TableCell<Tokimon, String>> cellFactory
                = (TableColumn<Tokimon, String> p) -> new EditingCell();

        idColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);
        weightColumn.setCellFactory(cellFactory);
        heightColumn.setCellFactory(cellFactory);
        //  set it can be modify
        idColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Tokimon, String> t) -> {
                    ((Tokimon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });
        //  set it can be modify
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Tokimon, String> t) -> {
                    ((Tokimon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });
        //  set it can be modify
        weightColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Tokimon, String> t) -> {
                    ((Tokimon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setWeight(t.getNewValue());
                });
        //  set it can be modify
        heightColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Tokimon, String> t) -> {
                    ((Tokimon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setHeight(t.getNewValue());
                });

        System.out.println("[initialize]: " + idColumn.getText() + " " + nameColumn.getText() + " " + weightColumn.getText() + " " + heightColumn.getText());

    }
}
