package com.example.testbottomnavigationview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private static TimeInterpolator sDefaultInterpolator;
    private ArrayList<ViewHolder> mPendingRemovals = new ArrayList();
    private ArrayList<ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<MyItemAnimator.MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<MyItemAnimator.ChangeInfo> mPendingChanges = new ArrayList();
    ArrayList<ArrayList<ViewHolder>> mAdditionsList = new ArrayList();
    ArrayList<ArrayList<MyItemAnimator.MoveInfo>> mMovesList = new ArrayList();
    ArrayList<ArrayList<MyItemAnimator.ChangeInfo>> mChangesList = new ArrayList();
    ArrayList<ViewHolder> mAddAnimations = new ArrayList();
    ArrayList<ViewHolder> mMoveAnimations = new ArrayList();
    ArrayList<ViewHolder> mRemoveAnimations = new ArrayList();
    ArrayList<ViewHolder> mChangeAnimations = new ArrayList();

    public MyItemAnimator() {
    }

    public void runPendingAnimations() {
        boolean removalsPending = !this.mPendingRemovals.isEmpty();
        boolean movesPending = !this.mPendingMoves.isEmpty();
        boolean changesPending = !this.mPendingChanges.isEmpty();
        boolean additionsPending = !this.mPendingAdditions.isEmpty();
        if (removalsPending || movesPending || additionsPending || changesPending) {
            Iterator var5 = this.mPendingRemovals.iterator();

            while (var5.hasNext()) {
                ViewHolder holder = (ViewHolder) var5.next();
                this.animateRemoveImpl(holder);
            }

            this.mPendingRemovals.clear();
            ArrayList additions;
            Runnable adder;
            if (movesPending) {
                additions = new ArrayList();
                additions.addAll(this.mPendingMoves);
                final ArrayList additionsIn = additions;
                this.mMovesList.add(additions);
                this.mPendingMoves.clear();
                adder = new Runnable() {
                    public void run() {
                        Iterator var1 = additionsIn.iterator();

                        while (var1.hasNext()) {
                            MyItemAnimator.MoveInfo moveInfo =
                                    (MyItemAnimator.MoveInfo) var1.next();
                            MyItemAnimator.this.animateMoveImpl(moveInfo.holder,
                                    moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                        }

                        additionsIn.clear();
                        MyItemAnimator.this.mMovesList.remove(additionsIn);
                    }
                };
                if (removalsPending) {
                    View view = ((MyItemAnimator.MoveInfo) additions.get
                            (0)).holder.itemView;
                    ViewCompat.postOnAnimationDelayed(view, adder, this.getRemoveDuration());
                } else {
                    adder.run();
                }
            }

            if (changesPending) {
                additions = new ArrayList();
                additions.addAll(this.mPendingChanges);
                this.mChangesList.add(additions);
                this.mPendingChanges.clear();

                final ArrayList additionsIn = additions;
                adder = new Runnable() {
                    public void run() {
                        Iterator var1 = additionsIn.iterator();

                        while (var1.hasNext()) {
                            MyItemAnimator.ChangeInfo change =
                                    (MyItemAnimator.ChangeInfo) var1.next();
                            MyItemAnimator.this.animateChangeImpl(change);
                        }

                        additionsIn.clear();
                        MyItemAnimator.this.mChangesList.remove(additionsIn);
                    }
                };
                if (removalsPending) {
                    ViewHolder holder = ((MyItemAnimator.ChangeInfo) additions.get
                            (0)).oldHolder;
                    ViewCompat.postOnAnimationDelayed(holder.itemView, adder,
                            this.getRemoveDuration());
                } else {
                    adder.run();
                }
            }

            if (additionsPending) {
                additions = new ArrayList();
                additions.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(additions);
                this.mPendingAdditions.clear();
                final ArrayList additionsIn = additions;
                adder = new Runnable() {
                    public void run() {
                        Iterator var1 = additionsIn.iterator();

                        while (var1.hasNext()) {
                            ViewHolder holder = (ViewHolder) var1.next();
                            MyItemAnimator.this.animateAddImpl(holder);
                        }

                        additionsIn.clear();
                        MyItemAnimator.this.mAdditionsList.remove(additionsIn);
                    }
                };
                if (!removalsPending && !movesPending && !changesPending) {
                    adder.run();
                } else {
                    long removeDuration = removalsPending ? this.getRemoveDuration() : 0L;
                    long moveDuration = movesPending ? this.getMoveDuration() : 0L;
                    long changeDuration = changesPending ? this.getChangeDuration() : 0L;
                    long totalDelay = removeDuration + Math.max(moveDuration, changeDuration);
                    View view = ((ViewHolder) additions.get(0)).itemView;
                    ViewCompat.postOnAnimationDelayed(view, adder, totalDelay);
                }
            }

        }
    }

    public boolean animateRemove(ViewHolder holder) {
        this.resetAnimation(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(final ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimator animation = view.animate();
        this.mRemoveAnimations.add(holder);
        animation.setDuration(this.getRemoveDuration()).alpha(0.0F).setListener(
                new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        MyItemAnimator.this.dispatchRemoveStarting(holder);
                    }

                    public void onAnimationEnd(Animator animator) {
                        animation.setListener((AnimatorListener) null);
                        view.setAlpha(1.0F);
                        MyItemAnimator.this.dispatchRemoveFinished(holder);
                        MyItemAnimator.this.mRemoveAnimations.remove(holder);
                        MyItemAnimator.this.dispatchFinishedWhenDone();
                    }
                }).start();
    }

    //添加item时调用，通常返回true
    @Override
    public boolean animateAdd(final RecyclerView.ViewHolder holder) {
        resetAnimation(holder);//重置动画，这个方法最终指向endAnimation，取消之前执行的动画，同时恢复Item的状态
        ViewCompat.setAlpha(holder.itemView, 0);//item执行动画之前的状态❤
        mPendingAdditions.add(holder);
        return true;
    }

    void animateAddImpl(final ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimator animation = view.animate();
        this.mAddAnimations.add(holder);
        animation.alpha(1.0F).setDuration(this.getAddDuration()).setListener(
                new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        MyItemAnimator.this.dispatchAddStarting(holder);
                    }

                    public void onAnimationCancel(Animator animator) {
                        view.setAlpha(1.0F);
                    }

                    public void onAnimationEnd(Animator animator) {
                        animation.setListener((AnimatorListener) null);
                        MyItemAnimator.this.dispatchAddFinished(holder);
                        MyItemAnimator.this.mAddAnimations.remove(holder);
                        MyItemAnimator.this.dispatchFinishedWhenDone();
                    }
                }).start();
    }

    public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        fromX += (int) holder.itemView.getTranslationX();
        fromY += (int) holder.itemView.getTranslationY();
        this.resetAnimation(holder);
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            this.dispatchMoveFinished(holder);
            return false;
        } else {
            if (deltaX != 0) {
                view.setTranslationX((float) (-deltaX));
            }

            if (deltaY != 0) {
                view.setTranslationY((float) (-deltaY));
            }

            this.mPendingMoves.add(new MyItemAnimator.MoveInfo(holder, fromX, fromY, toX,
                    toY));
            return true;
        }
    }

    void animateMoveImpl(final ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        final View view = holder.itemView;
        final int deltaX = toX - fromX;
        final int deltaY = toY - fromY;
        if (deltaX != 0) {
            view.animate().translationX(0.0F);
        }

        if (deltaY != 0) {
            view.animate().translationY(0.0F);
        }

        final ViewPropertyAnimator animation = view.animate();
        this.mMoveAnimations.add(holder);
        animation.setDuration(this.getMoveDuration()).setListener(new AnimatorListenerAdapter
                () {
            public void onAnimationStart(Animator animator) {
                MyItemAnimator.this.dispatchMoveStarting(holder);
            }

            public void onAnimationCancel(Animator animator) {
                if (deltaX != 0) {
                    view.setTranslationX(0.0F);
                }

                if (deltaY != 0) {
                    view.setTranslationY(0.0F);
                }

            }

            public void onAnimationEnd(Animator animator) {
                animation.setListener((AnimatorListener) null);
                MyItemAnimator.this.dispatchMoveFinished(holder);
                MyItemAnimator.this.mMoveAnimations.remove(holder);
                MyItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int
            fromY, int toX, int toY) {
        if (oldHolder == newHolder) {
            return this.animateMove(oldHolder, fromX, fromY, toX, toY);
        } else {
            float prevTranslationX = oldHolder.itemView.getTranslationX();
            float prevTranslationY = oldHolder.itemView.getTranslationY();
            float prevAlpha = oldHolder.itemView.getAlpha();
            this.resetAnimation(oldHolder);
            int deltaX = (int) ((float) (toX - fromX) - prevTranslationX);
            int deltaY = (int) ((float) (toY - fromY) - prevTranslationY);
            oldHolder.itemView.setTranslationX(prevTranslationX);
            oldHolder.itemView.setTranslationY(prevTranslationY);
            oldHolder.itemView.setAlpha(prevAlpha);
            if (newHolder != null) {
                this.resetAnimation(newHolder);
                newHolder.itemView.setTranslationX((float) (-deltaX));
                newHolder.itemView.setTranslationY((float) (-deltaY));
                newHolder.itemView.setAlpha(0.0F);
            }

            this.mPendingChanges.add(new MyItemAnimator.ChangeInfo(oldHolder, newHolder,
                    fromX, fromY, toX, toY));
            return true;
        }
    }

    void animateChangeImpl(final MyItemAnimator.ChangeInfo changeInfo) {
        ViewHolder holder = changeInfo.oldHolder;
        final View view = holder == null ? null : holder.itemView;
        ViewHolder newHolder = changeInfo.newHolder;
        final View newView = newHolder != null ? newHolder.itemView : null;
        ViewPropertyAnimator newViewAnimation;
        if (view != null) {
            newViewAnimation = view.animate().setDuration(this.getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            newViewAnimation.translationX((float) (changeInfo.toX - changeInfo.fromX));
            newViewAnimation.translationY((float) (changeInfo.toY - changeInfo.fromY));

            final ViewPropertyAnimator newViewAnimationIn = newViewAnimation;
            newViewAnimationIn.alpha(0.0F).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    MyItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder,
                            true);
                }

                public void onAnimationEnd(Animator animator) {
                    newViewAnimationIn.setListener((AnimatorListener) null);
                    view.setAlpha(1.0F);
                    view.setTranslationX(0.0F);
                    view.setTranslationY(0.0F);
                    MyItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder,
                            true);
                    MyItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    MyItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }

        if (newView != null) {
            newViewAnimation = newView.animate();

            final ViewPropertyAnimator newViewAnimationIn = newViewAnimation;
            this.mChangeAnimations.add(changeInfo.newHolder);
            newViewAnimationIn.translationX(0.0F).translationY(0.0F).setDuration
                    (this.getChangeDuration()).alpha(1.0F).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    MyItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder,
                            false);
                }

                public void onAnimationEnd(Animator animator) {
                    newViewAnimationIn.setListener((AnimatorListener) null);
                    newView.setAlpha(1.0F);
                    newView.setTranslationX(0.0F);
                    newView.setTranslationY(0.0F);
                    MyItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder,
                            false);
                    MyItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    MyItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }

    }

    private void endChangeAnimation(List<MyItemAnimator.ChangeInfo> infoList, ViewHolder
            item) {
        for (int i = infoList.size() - 1; i >= 0; --i) {
            MyItemAnimator.ChangeInfo changeInfo = (MyItemAnimator.ChangeInfo)
                    infoList.get(i);
            if (this.endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder
                    == null && changeInfo.newHolder == null) {
                infoList.remove(changeInfo);
            }
        }

    }

    private void endChangeAnimationIfNecessary(MyItemAnimator.ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }

        if (changeInfo.newHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }

    }

    private boolean endChangeAnimationIfNecessary(MyItemAnimator.ChangeInfo changeInfo,
                                                  ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != item) {
                return false;
            }

            changeInfo.oldHolder = null;
            oldItem = true;
        }

        item.itemView.setAlpha(1.0F);
        item.itemView.setTranslationX(0.0F);
        item.itemView.setTranslationY(0.0F);
        this.dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(ViewHolder item) {
        View view = item.itemView;
        view.animate().cancel();

        int i;
        for (i = this.mPendingMoves.size() - 1; i >= 0; --i) {
            MyItemAnimator.MoveInfo moveInfo = (MyItemAnimator.MoveInfo)
                    this.mPendingMoves.get(i);
            if (moveInfo.holder == item) {
                view.setTranslationY(0.0F);
                view.setTranslationX(0.0F);
                this.dispatchMoveFinished(item);
                this.mPendingMoves.remove(i);
            }
        }

        this.endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            view.setAlpha(1.0F);
            this.dispatchRemoveFinished(item);
        }

        if (this.mPendingAdditions.remove(item)) {
            view.setAlpha(1.0F);
            this.dispatchAddFinished(item);
        }

        ArrayList moves;
        for (i = this.mChangesList.size() - 1; i >= 0; --i) {
            moves = (ArrayList) this.mChangesList.get(i);
            this.endChangeAnimation(moves, item);
            if (moves.isEmpty()) {
                this.mChangesList.remove(i);
            }
        }

        for (i = this.mMovesList.size() - 1; i >= 0; --i) {
            moves = (ArrayList) this.mMovesList.get(i);

            for (int j = moves.size() - 1; j >= 0; --j) {
                MyItemAnimator.MoveInfo moveInfo = (MyItemAnimator.MoveInfo)
                        moves.get(j);
                if (moveInfo.holder == item) {
                    view.setTranslationY(0.0F);
                    view.setTranslationX(0.0F);
                    this.dispatchMoveFinished(item);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(i);
                    }
                    break;
                }
            }
        }

        for (i = this.mAdditionsList.size() - 1; i >= 0; --i) {
            moves = (ArrayList) this.mAdditionsList.get(i);
            if (moves.remove(item)) {
                view.setAlpha(1.0F);
                this.dispatchAddFinished(item);
                if (moves.isEmpty()) {
                    this.mAdditionsList.remove(i);
                }
            }
        }

        if (this.mRemoveAnimations.remove(item)) {
            ;
        }

        if (this.mAddAnimations.remove(item)) {
            ;
        }

        if (this.mChangeAnimations.remove(item)) {
            ;
        }

        if (this.mMoveAnimations.remove(item)) {
            ;
        }

        this.dispatchFinishedWhenDone();
    }

    private void resetAnimation(ViewHolder holder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = (new ValueAnimator()).getInterpolator();
        }

        holder.itemView.animate().setInterpolator(sDefaultInterpolator);
        this.endAnimation(holder);
    }

    public boolean isRunning() {
        return !this.mPendingAdditions.isEmpty() || !this.mPendingChanges.isEmpty() || !
                this.mPendingMoves.isEmpty() || !this.mPendingRemovals.isEmpty() || !
                this.mMoveAnimations.isEmpty() || !this.mRemoveAnimations.isEmpty() || !
                this.mAddAnimations.isEmpty() || !this.mChangeAnimations.isEmpty() || !
                this.mMovesList.isEmpty() || !this.mAdditionsList.isEmpty() || !this.mChangesList.isEmpty();
    }

    void dispatchFinishedWhenDone() {
        if (!this.isRunning()) {
            this.dispatchAnimationsFinished();
        }

    }

    public void endAnimations() {
        int count = this.mPendingMoves.size();

        int listCount;
        for (listCount = count - 1; listCount >= 0; --listCount) {
            MyItemAnimator.MoveInfo item = (MyItemAnimator.MoveInfo)
                    this.mPendingMoves.get(listCount);
            View view = item.holder.itemView;
            view.setTranslationY(0.0F);
            view.setTranslationX(0.0F);
            this.dispatchMoveFinished(item.holder);
            this.mPendingMoves.remove(listCount);
        }

        count = this.mPendingRemovals.size();

        ViewHolder item;
        for (listCount = count - 1; listCount >= 0; --listCount) {
            item = (ViewHolder) this.mPendingRemovals.get(listCount);
            this.dispatchRemoveFinished(item);
            this.mPendingRemovals.remove(listCount);
        }

        count = this.mPendingAdditions.size();

        for (listCount = count - 1; listCount >= 0; --listCount) {
            item = (ViewHolder) this.mPendingAdditions.get(listCount);
            item.itemView.setAlpha(1.0F);
            this.dispatchAddFinished(item);
            this.mPendingAdditions.remove(listCount);
        }

        count = this.mPendingChanges.size();

        for (listCount = count - 1; listCount >= 0; --listCount) {
            this.endChangeAnimationIfNecessary((MyItemAnimator.ChangeInfo)
                    this.mPendingChanges.get(listCount));
        }

        this.mPendingChanges.clear();
        if (this.isRunning()) {
            listCount = this.mMovesList.size();

            int j;
            int i;
            ArrayList changes;
            for (i = listCount - 1; i >= 0; --i) {
                changes = (ArrayList) this.mMovesList.get(i);
                count = changes.size();

                for (j = count - 1; j >= 0; --j) {
                    MyItemAnimator.MoveInfo moveInfo = (MyItemAnimator.MoveInfo)
                            changes.get(j);
                    ViewHolder item1 = moveInfo.holder;
                    View view = item1.itemView;
                    view.setTranslationY(0.0F);
                    view.setTranslationX(0.0F);
                    this.dispatchMoveFinished(moveInfo.holder);
                    changes.remove(j);
                    if (changes.isEmpty()) {
                        this.mMovesList.remove(changes);
                    }
                }
            }

            listCount = this.mAdditionsList.size();

            for (i = listCount - 1; i >= 0; --i) {
                changes = (ArrayList) this.mAdditionsList.get(i);
                count = changes.size();

                for (j = count - 1; j >= 0; --j) {
                    ViewHolder item1 = (ViewHolder) changes.get(j);
                    View view = item1.itemView;
                    view.setAlpha(1.0F);
                    this.dispatchAddFinished(item1);
                    changes.remove(j);
                    if (changes.isEmpty()) {
                        this.mAdditionsList.remove(changes);
                    }
                }
            }

            listCount = this.mChangesList.size();

            for (i = listCount - 1; i >= 0; --i) {
                changes = (ArrayList) this.mChangesList.get(i);
                count = changes.size();

                for (j = count - 1; j >= 0; --j) {
                    this.endChangeAnimationIfNecessary((MyItemAnimator.ChangeInfo)
                            changes.get(j));
                    if (changes.isEmpty()) {
                        this.mChangesList.remove(changes);
                    }
                }
            }

            this.cancelAll(this.mRemoveAnimations);
            this.cancelAll(this.mMoveAnimations);
            this.cancelAll(this.mAddAnimations);
            this.cancelAll(this.mChangeAnimations);
            this.dispatchAnimationsFinished();
        }
    }

    void cancelAll(List<ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; --i) {
            ((ViewHolder) viewHolders.get(i)).itemView.animate().cancel();
        }

    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull
            List<Object> payloads) {
        return !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads);
    }

    private static class ChangeInfo {
        public ViewHolder oldHolder;
        public ViewHolder newHolder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX,
                   int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder
                    + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" +
                    this.toY + '}';
        }
    }

    private static class MoveInfo {
        public ViewHolder holder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        MoveInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }
}
