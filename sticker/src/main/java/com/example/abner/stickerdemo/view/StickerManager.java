package com.example.abner.stickerdemo.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import com.example.abner.sticker.R;

public class StickerManager {
    private ViewGroup parent;
    private StickerView mCurrentSticker;
    private BubbleTextView mCurrentBubble;
    private Stickable mCurrentStickable;

    private List<Stickable> mStickables;

    private BubbleInputDialog mBubbleInputDialog;

    public StickerManager(ViewGroup parent) {
        this.parent = parent;

        mStickables = new ArrayList<>();
        mBubbleInputDialog = new BubbleInputDialog(parent.getContext());
        mBubbleInputDialog.setCompleteCallBack(new BubbleInputDialog.CompleteCallBack() {
            @Override
            public void onComplete(View bubbleTextView, String str) {
                ((BubbleTextView) bubbleTextView).setText(str);
            }
        });
    }

    public void commit() {
        for (Stickable s : mStickables) {
            s.commit();
        }
    }

    public void commitCurrent() {
        commitCurrentBubble();
        commitCurrentSticker();
    }

    public void commitCurrentBubble() {
        if (mCurrentBubble != null) mCurrentBubble.commit();
    }

    public void commitCurrentSticker() {
        if (mCurrentSticker != null) mCurrentSticker.commit();
    }

    public void addSticker() {
        addSticker(R.mipmap.ic_cat);
    }

    public void addSticker(/*@Drawable/@Res*/ int imageId) {
        final StickerView stickerView = new StickerView(parent.getContext());
        stickerView.setImageResource(imageId);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mStickables.remove(stickerView);
                parent.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                edit(stickerView);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mStickables.indexOf(stickerView);
                if (position == mStickables.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mStickables.remove(position);
                mStickables.add(mStickables.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parent.addView(stickerView, lp);
        mStickables.add(stickerView);
        edit(stickerView);
    }

    public synchronized void clear() {
        for (Stickable s : mStickables) {
            parent.removeView((View) s);
            mStickables.remove(s);
        }
    }

    public synchronized void unhide() {
        for (Stickable s : mStickables) {
            ((View) s).setVisibility(View.VISIBLE);
        }
    }

    public synchronized void hide() {
        for (Stickable s : mStickables) {
            ((View) s).setVisibility(View.GONE);
        }
    }

    public void addBubble() {
        addBubble(R.mipmap.bubble_7_rb);
    }

    public void addBubble(/*@Drawable/@Res*/ int imageId) {
        final BubbleTextView bubbleTextView = new BubbleTextView(parent.getContext(), android.graphics.Color.WHITE, 0);
        bubbleTextView.setImageResource(imageId);
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mStickables.remove(bubbleTextView);
                parent.removeView(bubbleTextView);
            }

            @Override
            public void onEdit(BubbleTextView bubbleTextView) {
                edit(bubbleTextView);
            }

            @Override
            public void onClick(BubbleTextView bubbleTextView) {
                mBubbleInputDialog.setBubbleTextView(bubbleTextView);
                mBubbleInputDialog.show();
            }

            @Override
            public void onTop(BubbleTextView bubbleTextView) {
                int position = mStickables.indexOf(bubbleTextView);
                if (position == mStickables.size() - 1) {
                    return;
                }
                BubbleTextView textView = (BubbleTextView) mStickables.remove(position);
                mStickables.add(mStickables.size(), textView);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parent.addView(bubbleTextView, lp);
        mStickables.add(bubbleTextView);
        edit(bubbleTextView);
    }

    private void edit(StickerView stickerView) {
        if (mCurrentStickable != null) mCurrentStickable.commit();
        mCurrentSticker = stickerView;
        mCurrentStickable = stickerView;
        stickerView.edit();
    }

    private void edit(BubbleTextView bubbleTextView) {
        if (mCurrentStickable != null) mCurrentStickable.commit();
        mCurrentBubble = bubbleTextView;
        mCurrentStickable = bubbleTextView;
        bubbleTextView.edit();
    }

}
