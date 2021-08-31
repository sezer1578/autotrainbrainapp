package com.hms.atbotizmozel.persistencehelpers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistence.MemberDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MemberRepository {
    private MemberDao mMemberDao;
    private LiveData<List<Member>> mAllMember;
    private LiveData<Member> mMember;


    public MemberRepository(Application application){
        MemberDatabase db = MemberDatabase.getDatabase(application);
        mMemberDao = db.memberDao();
        mAllMember = mMemberDao.getAllMembers();
    }


    LiveData<List<Member>> getAllMember() { return mAllMember; }

    public void insert (Member member){
        new insertAsyncTask(mMemberDao).execute(member);
    }

    public void updateMember(Member member){ new updateMembersAsyncTask(mMemberDao).execute(member);}

    public void deleteAll()  { new deleteAllMembersAsyncTask(mMemberDao).execute(); }

    public void deleteMember(Member member)  { new deleteMemberAsyncTask(mMemberDao).execute(member); }

    public Member getMember(Integer id){
        AsyncTask<Integer, Void, Member> asyncTask = new getMembersAsyncTask(mMemberDao, id).execute(id);
        try {
            return asyncTask.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }



    private static class insertAsyncTask extends AsyncTask<Member, Void, Void>{
        private MemberDao mAsyncTaskDao;
        insertAsyncTask(MemberDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Member... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllMembersAsyncTask extends AsyncTask<Void, Void, Void> {
        private MemberDao mAsyncTaskDao;

        deleteAllMembersAsyncTask(MemberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    private static class deleteMemberAsyncTask extends AsyncTask<Member, Void, Void> {
        private MemberDao mAsyncTaskDao;

        deleteMemberAsyncTask(MemberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Member... params) {
            mAsyncTaskDao.deleteMember(params[0]);
            return null;
        }
    }

    private static class updateMembersAsyncTask extends AsyncTask<Member, Void, Void> {
        private MemberDao mAsyncTaskDao;

        updateMembersAsyncTask(MemberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Member... params) {
            mAsyncTaskDao.updateMember(params[0]);
            return null;
        }
    }

    private static class getMembersAsyncTask extends AsyncTask<Integer, Void, Member> {
        private MemberDao mAsyncTaskDao;
        private Integer id;

        getMembersAsyncTask(MemberDao dao, Integer id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Member doInBackground(final Integer... params) {
           return mAsyncTaskDao.getMember(params[0]);

        }
    }


}
