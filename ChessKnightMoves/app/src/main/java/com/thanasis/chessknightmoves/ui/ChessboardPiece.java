package com.thanasis.chessknightmoves.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.thanasis.chessknightmoves.utils.Utils;

public class ChessboardPiece extends AppCompatImageView {
    private String pieceId;
    private int pieceColor;

    public ChessboardPiece(Context context, String id, int color, int width, int height) {
        super(context);

        this.pieceId = id;
        this.pieceColor = color;

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        this.setLayoutParams(parms);

        this.setBackgroundColor(pieceColor);

        init();
    }

    public String getPieceId() {
        return pieceId;
    }

    public void addColorFilter(int color) {
        this.setBackgroundColor(color);
    }

    public void init() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked: "+pieceId, Toast.LENGTH_SHORT).show();
            }
        });

        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(Utils.getStartPosition() == null) {
                    Utils.updateStartPosition(getPieceId());
                    addColorFilter(Color.GREEN);

                    Toast.makeText(getContext(), "Start Position: "+pieceId, Toast.LENGTH_SHORT).show();
                }
                else if(Utils.getEndPosition() == null) {
                    Utils.updateEndPosition(getPieceId());
                    addColorFilter(Color.RED);

                    Toast.makeText(getContext(), "End Position: "+pieceId, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Both start and end position are defined!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }
}
