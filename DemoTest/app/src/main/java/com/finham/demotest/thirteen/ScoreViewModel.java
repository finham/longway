package com.finham.demotest.thirteen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Android Studio.
 * User: Fin
 * Date: 2020/1/24
 * Time: 17:32
 */
public class ScoreViewModel extends ViewModel {
    private MutableLiveData<Integer> aTeamScore; //mutable可变的，会变的
    private MutableLiveData<Integer> bTeamScore;
    private int aBack,bBack;  //在add之前把之前的值保存起来就好了
    //两个Get方法
    public MutableLiveData<Integer> getaTeamScore() {
        if(aTeamScore==null){
            aTeamScore=new MutableLiveData<>();
            aTeamScore.setValue(0);
        }
        return aTeamScore;
    }

    public MutableLiveData<Integer> getbTeamScore() {
        if(bTeamScore==null){
            bTeamScore=new MutableLiveData<>();
            bTeamScore.setValue(0);
        }
        return bTeamScore;
    }

    public void aTeamAdd(int p){
        aBack=aTeamScore.getValue();
        bBack=bTeamScore.getValue();
        aTeamScore.setValue(aTeamScore.getValue()+p);
    }

    public void bTeamAdd(int p){
        aBack=aTeamScore.getValue();
        bBack=bTeamScore.getValue();
        bTeamScore.setValue(bTeamScore.getValue()+p);
    }

    public void reset(){
        aBack=aTeamScore.getValue();
        bBack=bTeamScore.getValue();
        aTeamScore.setValue(0);
        bTeamScore.setValue(0);
    }
    public void undo(){
        aTeamScore.setValue(aBack);
        bTeamScore.setValue(bBack);
    }
}
