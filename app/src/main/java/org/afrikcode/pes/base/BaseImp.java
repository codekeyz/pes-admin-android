package org.afrikcode.pes.base;

public abstract class BaseImp<T extends BaseView> {

    public T view;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }
}
