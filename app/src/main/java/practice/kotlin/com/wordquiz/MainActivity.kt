package practice.kotlin.com.wordquiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*


import java.util.*

class MainActivity : AppCompatActivity() {

    val t = R.string.english_0
    val t1 = t + 1
    val mQuestions = arrayOf(
        Question(R.string.english_0, R.string.korean_0), Question(R.string.english_1, R.string.korean_1), Question(
            R.string.english_2,
            R.string.korean_2
        ), Question(R.string.english_3, R.string.korean_3), Question
            (R.string.english_4, R.string.korean_4)
    )

    private var mCurrentNumber: Int = 0
    private var mAnswer = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUi()
        setButton()
        update.setOnClickListener {
            val retrofitService = getService()
            retrofitService.getUser("h")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe({ it ->
                    Log.d("content", it.toString())
                    Log.d("userIdx ", it.getAsJsonArray("data").get(0).asJsonObject.get("userIdx").asString)
                    Log.d("name ", it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString)
                    Log.d("part ", it.getAsJsonArray("data").get(0).asJsonObject.get("part").asString)
                    Log.d("profileUrl ", it.getAsJsonArray("data").get(0).asJsonObject.get("profileUrl").asString)
                    Log.d("message", it.getAsJsonPrimitive("message").asString)
                    Log.d("status", it.getAsJsonPrimitive("status").asString)*/
                .subscribe({
                    Log.d("content", it.toString())
                    Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                    //it.getAsJsonPrimitive("message").asString
                    //val test = it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString
                    retrofitService.getUser("s")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("content", it.toString())
                            Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                        }
                })
                {
                    Log.e("Error", it.message)
                }
        }
    }

    private fun updateUi() {
        showQuestion()
        setAnswerData()
        setAnswerButtonText()
    }

    private fun showQuestion() {
        question_text.setText("${mCurrentNumber + 1}" + ") " + resources.getString(R.string.question_title))
        question_word_text.setText(resources.getString(mQuestions[mCurrentNumber].english))

    }

    private fun setButton() {
        previous_btn.setOnClickListener {
            mCurrentNumber = mCurrentNumber - 1
            if (mCurrentNumber < 0) {
                mCurrentNumber = mQuestions.size - 1
            }
            showQuestion()
            updateUi()
        }
        next_btn.setOnClickListener {
            mCurrentNumber = (mCurrentNumber + 1) % mQuestions.size
            showQuestion()
            updateUi()
        }
        answer_one.setOnClickListener {
            if (resources.getString(mQuestions[mCurrentNumber].korean) == resources.getString((mQuestions[mAnswer.get(0)].korean))) {
                Toast.makeText(this, R.string.answer_true, Toast.LENGTH_SHORT).show()
                showSettingPopup()
            } else
                Toast.makeText(this, R.string.answer_false, Toast.LENGTH_SHORT).show()
        }
        answer_two.setOnClickListener {
            if (resources.getString(mQuestions[mCurrentNumber].korean) == resources.getString((mQuestions[mAnswer.get(1)].korean))) {
                Toast.makeText(this, R.string.answer_true, Toast.LENGTH_SHORT).show()
                showSettingPopup()
            } else
                Toast.makeText(this, R.string.answer_false, Toast.LENGTH_SHORT).show()
        }
        answer_three.setOnClickListener {
            if (resources.getString(mQuestions[mCurrentNumber].korean) == resources.getString((mQuestions[mAnswer.get(2)].korean))) {
                Toast.makeText(this, R.string.answer_true, Toast.LENGTH_SHORT).show()
                showSettingPopup()
            } else
                Toast.makeText(this, R.string.answer_false, Toast.LENGTH_SHORT).show()
        }
        answer_four.setOnClickListener {
            if (resources.getString(mQuestions[mCurrentNumber].korean) == resources.getString((mQuestions[mAnswer.get(3)].korean))) {
                Toast.makeText(this, R.string.answer_true, Toast.LENGTH_SHORT).show()
                showSettingPopup()
            } else
                Toast.makeText(this, R.string.answer_false, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSettingPopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.alert, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("축하합니다!! 정답입니다")
            .setPositiveButton("다음") { dialog, which ->
                next_btn.performClick()
            }
            .setNegativeButton("다시", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
        alertDialog.window?.setLayout(1000, 800)
    }

    private fun setAnswerData() {
        var isDuplicated = false
        mAnswer.clear()
        mAnswer.add(0, -1)
        mAnswer.add(1, -1)
        mAnswer.add(2, -1)
        mAnswer.add(3, -1)

        var cnt = 0
        val random = Random()

        var temp: Int
        while (true) {
            temp = random.nextInt(mQuestions.size - 1)
            if (temp == mCurrentNumber) {
                continue
            }
            for (i in 0..2) {
                if (temp == mAnswer.get(i)) {
                    isDuplicated = true
                }
            }
            if (isDuplicated) {
                isDuplicated = false
                continue
            } else {
                mAnswer.set(cnt, temp)
                cnt++
            }
            if (cnt > 2) {
                break
            }
        }
        mAnswer.set(3, mCurrentNumber)
        Collections.shuffle(mAnswer)
    }

    private fun setAnswerButtonText() {
        answer_one.setText(resources.getString(mQuestions[mAnswer.get(0)].korean))
        answer_two.setText(resources.getString(mQuestions[mAnswer.get(1)].korean))
        answer_three.setText(resources.getString(mQuestions[mAnswer.get(2)].korean))
        answer_four.setText(resources.getString(mQuestions[mAnswer.get(3)].korean))
    }

}
