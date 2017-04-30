package me.minidigger.projecttd.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import me.minidigger.projecttd.ProjectTD;

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


    public HudScene(SpriteBatch batch) {
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
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.format("%06d", score) + " PTS");
    }

    public void setBalance(int balance) {
        this.balance = balance;
        balanceLabel.setText(String.format("%06d", balance) + " $");
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
}
