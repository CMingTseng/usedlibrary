package com.example.basic;

/**
 * 项目名称：com.example.demo
 * 项目版本：usedlibrary
 * 创建人：Daimhim
 * 创建时间：2017/8/2 14:46
 * 修改人：Daimhim
 * 修改时间：2017/8/2 14:46
 * 类描述：
 * 修改备注：
 */

public abstract class BasePresenter<T extends BaseContract.View>  implements BaseContract.Presenter<T>{
    String TAG = "TAG:" + getClass().getSimpleName();

    T mView;

    @Override
    public abstract void onStart(T view);

    @Override
    public void onStop() {

    }
}
