package drawerItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droid.solver.a2020.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout myLayout,myLayout2;
    AnimationDrawable mAnimationDrawable;
    CardView mCardView1,mCardView2,mCardView3,mCardView4,mCardView5,mCardView6,mCardView7,mCardView8,finalScoreCardView,finalScore,playAgainCardView,exitCardView;
    int flag=1,flag1=0,correctPostion,totalQuestionAsked=0,noOfCorrectAnswer=0,highScore1=0;
    ImageView imageView,imageView2;
    TextView question,option1,option2,option3,option4,correct,timer,highScore,playAgainTextView,exitTextView,finalScoreTextView;
    String correctAnswer,imageUrl;
    CountDownTimer countDownTimer;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    public static final String pref="My Pref File";
    List<Questions> questionsList,questionsList1;
    Questions questions;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        init();
        questionsList = new ArrayList<>();
        questionsList1 = new ArrayList<>();
        preferences = getSharedPreferences(pref,MODE_PRIVATE);
        String x = preferences.getString("highScore","0");
        highScore1 = Integer.parseInt(x);
        highScore.setText(x);
        feedArrayList();
        generateQuestion();
    }

    private void init() {
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        correct = findViewById(R.id.correct);
        timer = findViewById(R.id.timer);
        highScore = findViewById(R.id.highScore);
        myLayout =  findViewById(R.id.myLayout);
        myLayout2 = findViewById(R.id.myLayout2);
        mCardView1 =  findViewById(R.id.cardView1);
        mCardView2 = findViewById(R.id.cardView2);
        mCardView3 = findViewById(R.id.cardView3);
        mCardView4 = findViewById(R.id.cardView4);
        mCardView5 = findViewById(R.id.cardView5);
        mCardView6 = findViewById(R.id.cardView6);
        mCardView7 = findViewById(R.id.cardView7);
        mCardView8 = findViewById(R.id.cardView8);
        finalScoreCardView=findViewById(R.id.finalScoreCard);
        finalScore = findViewById(R.id.finalScore);
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        playAgainCardView = findViewById(R.id.playAgainCardView);
        playAgainTextView = findViewById(R.id.playAgainText);
        exitCardView = findViewById(R.id.exitCardView);
        exitTextView = findViewById(R.id.exitTextView);
        progressDialog = new ProgressDialog(QuizActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mCardView2.setOnClickListener(this);
        mCardView3.setOnClickListener(this);
        mCardView4.setOnClickListener(this);
        mCardView5.setOnClickListener(this);
        playAgainCardView.setOnClickListener(this);
        exitCardView.setOnClickListener(this);
        mCardView2.setEnabled(false);
        mCardView3.setEnabled(false);
        mCardView4.setEnabled(false);
        mCardView5.setEnabled(false);
        editor = getSharedPreferences(pref,MODE_PRIVATE).edit();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cardView2:
                countDownTimer.cancel();
                checkAnswer("1",mCardView2);
                break;
            case R.id.cardView3:
                countDownTimer.cancel();
                checkAnswer("2",mCardView3);
                break;
            case R.id.cardView4:
                countDownTimer.cancel();
                checkAnswer("3",mCardView4);
                break;
            case R.id.cardView5:
                countDownTimer.cancel();
                checkAnswer("4",mCardView5);
                break;
            case R.id.playAgainCardView:
                generateNewGame();
                break;
            case R.id.exitCardView:
                exitGame();
                break;
        }
    }

    private void generateNewGame() {
        flag1=0;
        questionsList=new ArrayList<Questions>(questionsList1);
        playAgainCardView.setCardBackgroundColor(Color.rgb(85,139,47));
        startThirdTimer();
    }

    private void exitGame() {
        exitCardView.setCardBackgroundColor(Color.rgb(85,139,47));
        finish();
    }


    public void feedArrayList(){
        questions = new Questions("Which city is called the bangle city of india?","Agra","Lucknow","Firozabad","Gurugram","Firozabad","https://images-na.ssl-images-amazon.com/images/I/51bmMB90vqL.jpg");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Elephant festival is celebrated in?","Jaipur","Jodhpur","Kota","Ajmer","Jaipur","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question1.jpg?alt=media&token=723e3fe3-5b0c-43de-92f6-4966d31e6dac");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Kaziranga Wildlife Sanctuary is in:","Madhya Pradesh","Orrisa","Assam","Nagaland","Assam","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question2.jpg?alt=media&token=b1e2809d-8df7-4e36-a63a-e6f7ac9f7958");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("In which state is the image monument?","Kerela","Punjab","Sikkim","Uttar Pradesh","Uttar Pradesh","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question3.jpg?alt=media&token=f29b7813-3f8d-4c6a-933c-1128f215023c");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The Centre for Cellular and Molecular Biology is situated at","Patna","Jaipur","Hyderabad","New Delhi","Hyderabad","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question4.jpg?alt=media&token=1c24a744-63fc-4967-8d77-5dcec2ce743f");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The famous Dilwara Temples are situated in","Uttar Pradesh","Rajasthan","Maharastra","Madhya Pradesh","Rajasthan","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question5.jpg?alt=media&token=a6f5315a-5f98-46d7-87e2-dcc97ad4f87e");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Wadia Institute of Himalayan Geology is located at","Delhi","Shimla","Dehradun","Kulu","Dehradun","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question6.jpg?alt=media&token=2531ddfc-2520-459b-99b0-6396ca3701d5");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The world famous 'Khajuraho' sculptures are located in","Gujarat","Madhya Pradesh","Orissa","Maharastra","Madhya Pradesh","\"https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question7.jpg?alt=media&token=cddf6ee5-a39b-450a-ba94-bf87cf96cc58\"");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("'Kandla' is situated on the Gulf of Kachh is well known for which of the following?","Export Processing Zone","Centre for Marine Food products","Cutting and Polishing of diamonds","Ship breaking industry","Export Processing Zone","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question8.jpg?alt=media&token=a0d81130-4ec7-46b6-8672-7e246736cbf5");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Vikram Sarabhai Space Centre is located at","Pune","Ahmedabad","Sriharikota","Thiruvananthapuram","Thiruvananthapuram","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question9.jpg?alt=media&token=0bf84faa-a8c3-450c-8fe8-ed03ed96088f");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The National Institute of Community Development is located at","Chennai","Pant Nagar","Hyderabad","Bangalore","Hyderabad","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question10.jpg?alt=media&token=3d3a1d14-ab71-4dd5-88f2-21f3e9045de0");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The famous Rock Garden is located in which city?","Jaipur","Lucknow","Shimla","Chandigarh","Chandigarh","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question11.jpg?alt=media&token=28481d88-4d7d-43c4-aa5c-c7975b7450ea");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Sanchi Stupa is located near","Gaya","Bhopal","Varanasi","Bijapur","Bhopal","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question12.jpg?alt=media&token=f1c7c392-fb96-4646-b2c6-75c54cc5a56a");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Indian Cancer Research institute is located at","New Delhi","Calcutta","Chennai","Mumbai","Mumbai","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question13.jpg?alt=media&token=5ff68b6b-8462-4b78-a137-b55efa6158a1");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Central Drug Research Institute is located at","Trissur","Nagpur","Mysore","Lucknow","Lucknow","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question14.jpg?alt=media&token=c71402da-6f0d-4435-ab3a-0b6fc038bd8b");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Ajanta-Ellora caves are situated near","Ajmer","Jaipur","Patna","Aurangabad","Aurangabad","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question15.jpg?alt=media&token=da0d2921-4f39-481e-9686-c88ff54df6df");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("National School of Mines is located in ?","Dhanbad","Kavalpur","Udaipur","Hyderabad","Dhanbad","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question16.jpg?alt=media&token=0a953e29-2509-4938-bf44-98cbee2ef405");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Sun Temple is situated at?","Konark","Bangalore","Haridwar","Kerela","Konark","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question17.jpg?alt=media&token=a5a49335-2a16-4f33-93ac-de01681bda5e");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Ms. Harita Kaur has the distinction of being the first Indian Women","pilot to fly an aircraft solo","ambassador to a foreign country","to be inducted into Indian Navy","doctor to create first test tube baby","pilot to fly an aircraft solo","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question18.jpg?alt=media&token=b215e7fe-b93e-4137-84f1-4fb817a649a5");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The largest and the oldest museum of India is located in the state/union territory of","New Delhi","West Bengal","Andhra Pradesh","Uttar Pradesh","West Bengal","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question19.jpg?alt=media&token=92491c6c-56b9-4191-b173-8b0d57e98392");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The first Indian Satellite launched from Soviet Cosmodrome is?","Bhaskara","Bharat","Rohini","Aryabhatta","Aryabhatta","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question20.jpg?alt=media&token=2e7ade5b-5b94-4ff9-926d-d582298b65ca");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("The first nuclear reactor in India is","Dhruva","Harsha","Vipula","Apsara","Apsara","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question21.jpg?alt=media&token=d0e1cf61-a882-4237-a98a-2f228cdd586b");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Largest Mint in India is located at","Nasik","Kolkata","Hyderabad","Mumbai","Kolkata","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question22.jpg?alt=media&token=b8752682-8937-4972-9251-c8031ece5305");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("National Institute of Aeronautical Engineering is located at","Dehradun","Lucknow","Bangalore","Delhi","Delhi","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question23.jpg?alt=media&token=92e85263-1e64-4010-8c81-7f5c07ffa4ee");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("National Institute of Nutrition is located in which of the following place?","Bangalore","Kerela","Gandhinagar","Hyderabad","Hyderabad","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question24.jpg?alt=media&token=6b2c871c-c3a3-4608-afb7-68760ddcbe22");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Bijapur is known for its","Gol Gumbaz","severe drought condition","heavy rainfall","statue of Gomateswara","Gol Gumbaz","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question25.jpg?alt=media&token=554477cd-8a1d-488b-8fd6-9a747ad36932");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Which city is called the City of Pearls?","Lucknow","Ahmedabad","Chennai","Hyderabad","Hyderabad","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question26.jpg?alt=media&token=ed60f349-48a1-4041-83f3-9a1174bfcee9");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Which city is named the Royal City?","New Delhi","Lucknow","Madurai","Patiala","Patiala","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question27.jpg?alt=media&token=f97ca3a2-dfa3-4282-89c7-9d6c96367c41");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Which of the following is the City of Diamonds?","Surat","Ahmedabad","Jaipur","Palanpur","Palanpur","https://firebasestorage.googleapis.com/v0/b/realtime-database-aa69e.appspot.com/o/question28.webp?alt=media&token=43f78310-fcb9-43d6-9951-81c9b608ef4e");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Which of the following is called the City of Weavers?","Coimbatore","Surat","Kanpur","Panipat","Panipat","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question29.jpg?alt=media&token=e239024e-c5c2-44ef-a1ad-ab17a19f0387");
        questionsList.add(questions);
        questionsList1.add(questions);
        questions = new Questions("Which of the following is the City of Temples?","Varanasi","Allahabad","Jaipur","Raipur","Varanasi","https://firebasestorage.googleapis.com/v0/b/quizzes-database.appspot.com/o/question30.jpg?alt=media&token=ab9dc86e-ca6b-4793-9a99-54da4658f6e5");
        questionsList.add(questions);
        questionsList1.add(questions);
    }

    public void generateQuestion(){
        Random random = new Random();
        if(questionsList.size()==0){
            questionsList=new ArrayList<Questions>(questionsList1);
        }
        int x = random.nextInt(questionsList.size());
        questions = questionsList.get(x);
        correctAnswer = questions.getAnswer();
        imageUrl = questions.getImageUrl();
        questionsList.remove(x);

        if(questions.getOption1().equals(correctAnswer)){
            correctPostion = 1;
        }
        else if(questions.getOption2().equals(correctAnswer)){
            correctPostion = 2;
        }
        else if(questions.getOption3().equals(correctAnswer)){
            correctPostion = 3;
        }
        else if(questions.getOption4().equals(correctAnswer)){
            correctPostion = 4;
        }
        downloadImage(imageUrl);
    }

    private void downloadImage(String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView2, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progressDialog.cancel();
                myLayout.setVisibility(View.VISIBLE);
                imageView.setImageDrawable(imageView2.getDrawable());
                mCardView2.setEnabled(true);
                mCardView3.setEnabled(true);
                mCardView4.setEnabled(true);
                mCardView5.setEnabled(true);
                mCardView2.setCardBackgroundColor(Color.rgb(44, 62, 80));
                mCardView3.setCardBackgroundColor(Color.rgb(44, 62, 80));
                mCardView4.setCardBackgroundColor(Color.rgb(44, 62, 80));
                mCardView5.setCardBackgroundColor(Color.rgb(44, 62, 80));
                question.setText(questions.getQuestions());
                option1.setText(questions.getOption1());
                option2.setText(questions.getOption2());
                option3.setText(questions.getOption3());
                option4.setText(questions.getOption4());
                showAnimation();
                startTimer();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(11000, 100) {
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished / 1000 + "s");
                if(millisUntilFinished<1000){
                    if(flag1==0) {
                        totalQuestionAsked++;
                        flag1 = 1;
                    }
                    correct.setText("Your Score: " + noOfCorrectAnswer + "/" + totalQuestionAsked);
                    mCardView2.setEnabled(false);
                    mCardView3.setEnabled(false);
                    mCardView4.setEnabled(false);
                    mCardView5.setEnabled(false);
                    if(correctPostion==1){
                        mCardView2.setCardBackgroundColor(Color.rgb(85,139,47));
                    }else if(correctPostion==2){
                        mCardView3.setCardBackgroundColor(Color.rgb(85,139,47));
                    }else if(correctPostion==3){
                        mCardView4.setCardBackgroundColor(Color.rgb(85,139,47));
                    }else{
                        mCardView5.setCardBackgroundColor(Color.rgb(85,139,47));
                    }
                }
            }

            public void onFinish() {
                timer.setText("0s");
                mCardView2.setEnabled(false);
                mCardView3.setEnabled(false);
                mCardView4.setEnabled(false);
                mCardView5.setEnabled(false);
                showFinalScore();
            }
        }.start();
    }



    public void checkAnswer(String tag,CardView cardView){
        mCardView2.setEnabled(false);
        mCardView3.setEnabled(false);
        mCardView4.setEnabled(false);
        mCardView5.setEnabled(false);
        cardView.setCardBackgroundColor(Color.rgb(98,0,238));
        startAnotherTimer(tag,cardView);
    }

    private void startAnotherTimer(final String tag,final CardView cardView) {

        if(tag.equals(Integer.toString(correctPostion))){
            noOfCorrectAnswer++;
            totalQuestionAsked++;
            if(noOfCorrectAnswer>highScore1){
                highScore1=noOfCorrectAnswer;
            }
            flag=0;
            new CountDownTimer(2000,200){

                @Override
                public void onTick(long millisUntilFinished) {
                    if(millisUntilFinished>600){
                        if(flag==1){
                            cardView.setCardBackgroundColor(Color.rgb(98,0,238));
                            flag=0;
                        }
                        else{
                            cardView.setCardBackgroundColor(Color.rgb(44,62,80));
                            flag=1;
                        }
                    }
                    else{
                        cardView.setCardBackgroundColor(Color.rgb(85,139,47));
                        correct.setText("Your Score : " + Integer.toString(noOfCorrectAnswer) + "/" + Integer.toString(totalQuestionAsked));
                        highScore.setText(Integer.toString(highScore1));
                        editor.putString("highScore",Integer.toString(highScore1));
                        editor.apply();
                    }
                }

                @Override
                public void onFinish() {
                    generateQuestion();
                }
            }.start();
        }else{
            totalQuestionAsked++;
            flag=0;
            new CountDownTimer(2000,200){

                @Override
                public void onTick(long millisUntilFinished) {
                    if(millisUntilFinished>600){
                        if(flag==1){
                            cardView.setCardBackgroundColor(Color.rgb(98,0,238));
                            flag=0;
                        }
                        else{
                            cardView.setCardBackgroundColor(Color.rgb(44,62,80));
                            flag=1;
                        }
                    }
                    else{
                        cardView.setCardBackgroundColor(Color.rgb(216,67,21));
                        if(correctPostion==1){
                            mCardView2.setCardBackgroundColor(Color.rgb(85,139,47));
                        }else if(correctPostion==2){
                            mCardView3.setCardBackgroundColor(Color.rgb(85,139,47));
                        }else if(correctPostion==3){
                            mCardView4.setCardBackgroundColor(Color.rgb(85,139,47));
                        }else{
                            mCardView5.setCardBackgroundColor(Color.rgb(85,139,47));
                        }
                        correct.setText("Your Score : " + Integer.toString(noOfCorrectAnswer) + "/" + Integer.toString(totalQuestionAsked));
                        highScore.setText(Integer.toString(highScore1));
                    }
                }

                @Override
                public void onFinish() {
                    showFinalScore();
                }
            }.start();
        }
    }

    public void showFinalScore(){
        finalScoreCardView.setVisibility(View.VISIBLE);
        finalScoreTextView.setText("Your Score: " + noOfCorrectAnswer + "/" + totalQuestionAsked);
    }

    private void startThirdTimer() {
        new CountDownTimer(300,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                myLayout.setVisibility(View.GONE);
                progressDialog.show();
                playAgainCardView.setCardBackgroundColor(Color.rgb(44,62,80));
                finalScoreCardView.setVisibility(View.GONE);
                correct.setText("Your Score : 0/0" );
                noOfCorrectAnswer=0;
                totalQuestionAsked=0;
                generateQuestion();

            }
        }.start();
    }

    public void showAnimation(){
        mAnimationDrawable = (AnimationDrawable) myLayout.getBackground();
        mAnimationDrawable.setEnterFadeDuration(1000);
        mAnimationDrawable.setExitFadeDuration(2000);
        mAnimationDrawable.start();
        mCardView1.setTranslationX(-1000f);
        ObjectAnimator animation = ObjectAnimator.ofFloat(mCardView1, "translationX", 0f);
        animation.setDuration(500);
        animation.start();
        mCardView2.setTranslationX(-1000f);
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(mCardView2, "translationX", 0f);
        animation1.setDuration(600);
        animation1.start();
        mCardView3.setTranslationX(-1000f);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(mCardView3, "translationX", 0f);
        animation2.setDuration(800);
        animation2.start();
        mCardView4.setTranslationX(-1000f);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(mCardView4, "translationX", 0f);
        animation3.setDuration(1000);
        animation3.start();
        mCardView5.setTranslationX(-1000f);
        ObjectAnimator animation4 = ObjectAnimator.ofFloat(mCardView5, "translationX", 0f);
        animation4.setDuration(1200);
        animation4.start();
    }

    @Override
    public void onBackPressed() {
//        if(progressDialog.isShowing()) {
//            progressDialog.cancel();
//        }
//        startActivity(new Intent(QuizActivity.this,MainActivity.class));
        finish();
    }
}
