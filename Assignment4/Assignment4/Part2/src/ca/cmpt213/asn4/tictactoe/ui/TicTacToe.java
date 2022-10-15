package ca.cmpt213.asn4.tictactoe.ui;

import ca.cmpt213.asn4.tictactoe.game.Game_info;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TicTacToe extends Application {

    public static boolean order = true;
    public static String winner = "";

    public static void main(String[] args) {
        launch(args);
    }
    /*
    @par index of array, data_info data type of array, boolean if whose turn
    @uses change the index of the two-dimensional int array, check if can click and click change to 1/-1
     */
    public boolean clicked_mouse (int index, Game_info data_info, boolean order) {
        int first = index/4;
        int second = index%4;
        if (data_info.getElement(first,second) != 0)
            return false;
        data_info.change_array(first,second,order);
        return true;

    }
    /*
    @par stage and gridpane
    @uses set the button
     */
    public void setButton (Stage primaryStage, GridPane gridPane){
        Button button = new Button("New Game");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                start(primaryStage);
            }
        });
        HBox hBox = new HBox(button);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.setPrefHeight(100);
        hBox.setPrefWidth(10);
        gridPane.add(hBox,2,5);
    }
    /*
    @par string of label and gridpane
    @uses set the text label
     */
    public void setTextLabel (GridPane gridPane, String winner){
        Text text1 = new Text(30,130,  winner + " is Winner " );
        text1.setFont(new Font("SanSerif",30));
        text1.setScaleX(0.5);
        text1.setScaleY(0.5);

        HBox hlabel = new HBox(text1);
        gridPane.add(hlabel,2,5);
    }
    /*
    @par stage and image of finished stage
    @uses set the winner image
     */
    public void setFinish (GridPane gridPane, Image finish){
        ImageView imageView = new ImageView(finish);
        HBox hBox =  new HBox(imageView);
        gridPane.add(hBox,5,0);
    }
    /*
    @par stage
    @uses start to setup the stage
     */
    @Override
    public void start(Stage primaryStage) {
        // Create six Button components.
        ArrayList<ImageView> image_set = new ArrayList<>();
        ArrayList<VBox> vbox_set = new ArrayList<>();
        Image red = new Image ("file:image/bear1.png");
        Image blue = new Image ("file:image/bear2.png");
        Image box = new Image("file:image/home.jpg");
        Image finish = new Image("file:image/111.jpg");
        GridPane gridPane = new GridPane();
        Game_info data_array = new Game_info();
        int index = 0;

        // using loop to set the each box and add the mouse handle
        for (int i =0;i<16;i++){
            image_set.add(i,new ImageView(box));
            image_set.get(i).setFitWidth(250);
            image_set.get(i).setFitHeight(175);
            int finalI = i;
            image_set.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (clicked_mouse(finalI,data_array,order)){
                        if (order){
                            order = false;
                            ((ImageView)mouseEvent.getSource()).setImage(red);
                        }
                        else {
                            ((ImageView) mouseEvent.getSource()).setImage(blue);
                            order = true;
                        }
                        if(data_array.check_if_first_win()) {
                            winner = "Grizz";
                            setTextLabel(gridPane,winner);
                            setFinish(gridPane,finish);
                        }
                        else if(data_array.check_if_second_win()) {
                            winner = "Panda&Ice";
                            setTextLabel(gridPane,winner);
                            setFinish(gridPane,finish);

                        }
                    }setButton(primaryStage,gridPane);
                }
            });
        }
        // using loop tp set the image box
        for (int i = 0; i<4; i++) {
            vbox_set.add(i,new VBox(10,image_set.get(index),image_set.get(index+1),image_set.get(index+2),image_set.get(index+3)));
            index+=4;
        }
        //  using loop to add vbox into the gridpane
        for (int i =0; i<4;i++){
            gridPane.add(vbox_set.get(i),i,0);
        }


        Scene scene = new Scene(gridPane);

        // Add the Scene to the Stage.
        primaryStage.setScene(scene);

        // Set the stage title.
        primaryStage.setTitle("TicTacToe");


        // Show the window.
        primaryStage.show();

    }
}

