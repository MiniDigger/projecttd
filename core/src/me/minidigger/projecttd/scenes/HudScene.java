package me.minidigger.projecttd.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.VisUI;
import me.minidigger.projecttd.utils.ArcRenderer;
import me.minidigger.projecttd.utils.RingButton;

/**
 * Created by Martin on 30/04/2017.
 */
public class HudScene implements Disposable {

    private Stage stage;
    private SpriteBatch batch;

    private int score = 0;
    private int balance = 0;

    private Label scoreLabel;
    private Label balanceLabel;

    private RingButton ringButton;

    public HudScene(SpriteBatch batch, ShapeRenderer shapeRenderer, ArcRenderer arcRenderer) {
        this.batch = batch;
        stage = new Stage();

        VisUI.load();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.VIOLET));
        setScore(score);
        balanceLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.VIOLET));
        setBalance(balance);

        table.add(scoreLabel).align(Align.left).expandX();
        table.add(balanceLabel).align(Align.right).expandX();

        stage.addActor(table);


        ringButton = new RingButton(shapeRenderer, arcRenderer);
        ringButton.setBounds(10, 10, 100, 100);
        stage.addActor(ringButton);
    }

    public void setScore(int score) {
        scoreLabel.setText(score + " PTS");
    }

    public void setBalance(int balance) {
        this.balance = balance;
        balanceLabel.setText(balance + " $");
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void render() {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.act();
        stage.draw();
    }

    public int getBalance() {
        return balance;
    }

    public int getScore() {
        return score;
    }
}
