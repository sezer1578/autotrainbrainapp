package com.hms.atbotizmozel.persistencehelpers;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hms.atbotizmozel.persistence.Member;

import java.util.List;

public class MemberViewModel extends AndroidViewModel {
    private MemberRepository mRepository;
    private LiveData<List<Member>> mAllMember;

    public MemberViewModel(Application application){
        super(application);
        mRepository= new MemberRepository(application);
        mAllMember = mRepository.getAllMember();
    }

    public LiveData<List<Member>> getmAllMember(){
        return mAllMember;
    }

    public void insert(Member member){
        mRepository.insert(member);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteMember(Member member) {mRepository.deleteMember(member);}

    public void updateMember(Member member) {mRepository.updateMember(member);}

    public Member getMember(Integer id){
        return mRepository.getMember(id);
    }


}
