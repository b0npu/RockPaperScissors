package com.b0npu.rpsgame

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import android.widget.{ImageButton, ImageView, TextView}

class MainActivity extends AppCompatActivity with TypedFindView {

  /**
    * フィールドの定義
    *
    * widgetのidを格納する変数とじゃんけんで使う変数を定義する
    * じゃんけんで使う変数は変更しないのでvalで定義
    */
  var rockButton: ImageButton = _
  var scissorButton: ImageButton = _
  var paperButton: ImageButton = _
  var playerMove: ImageView = _
  var enemyMove: ImageView = _
  var resultText: TextView = _

  val handSigns = Map[String, Int](
    "Rock" → R.drawable.rock,
    "Scissor" → R.drawable.scissor,
    "Paper" → R.drawable.paper
  )
  val handsArray = handSigns.keys.toArray

  /**
    * アプリの画面を生成
    *
    * アプリを起動するとonCreateが呼ばれてActivityが初期化され
    * setContentViewでレイアウトがビューに表示される
    */
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /**
      * widgetのidを取得
      *
      * sbt-androidプラグインのTyped Resources(TR)を使って
      * レイアウトに設置したwidgetのidを変数に格納する
      */
    rockButton = findView(TR.rockButton)
    scissorButton = findView(TR.scissorButton)
    paperButton = findView(TR.paperButton)

    playerMove = findView(TR.playerMove)
    enemyMove = findView(TR.enemyMove)
    resultText = findView(TR.resultText)

    /**
      * ButtonをClickしてじゃんけん
      *
      * ClickしたButtonのImageをRPSGameメソッドに渡してじゃんけんする
      */
    rockButton.setOnClickListener(new OnClickListener() {
      override def onClick(v: View): Unit = {
        RPSGame(handsArray.indexOf("Rock"))
      }
    })
    scissorButton.setOnClickListener(new OnClickListener() {
      override def onClick(v: View): Unit = {
        RPSGame(handsArray.indexOf("Scissor"))
      }
    })
    paperButton.setOnClickListener(new OnClickListener() {
      override def onClick(v: View): Unit = {
        RPSGame(handsArray.indexOf("Paper"))
      }
    })
  }

  /**
    * じゃんけんのメソッド
    *
    * 引数でplayerのhandSignを受け取ってRandomでenemyのhandSignを決める
    * handSignはRock: 0, Scissor: 1, Paper: 2の数字を割り当てているので
    * ((playerSign - enemySign) + 3 ) % 3の結果で勝敗が決まる
    * 0: Draw, 1: Lose, 2: Win
    */
  private def RPSGame(playerSign: Int): Unit = {
    val gameResult = Map[Int, String](
      0 → "Draw",
      1 → "Oh You Lose...",
      2 → "You Win!!"
    )
    val enemySign = scala.util.Random.nextInt(3)
    val result = (playerSign - enemySign + 3) % 3

    playerMove.setImageResource(handSigns(handsArray(playerSign)))
    enemyMove.setImageResource(handSigns(handsArray(enemySign)))
    resultText.setText(gameResult(result))
  }
}