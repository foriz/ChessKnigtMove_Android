package com.thanasis.chessknightmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.thanasis.chessknightmoves.core.Algorithm;
import com.thanasis.chessknightmoves.ui.ChessboardPiece;
import com.thanasis.chessknightmoves.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends Activity {
    int screenWidth = 0;
    int screenHeight = 0;

    TranslateAnimation[] animations;
    int playAnimationCounter = 0;

    String moveSequence = null;

    ImageView first_white_knight;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // Each chessboard rectangle has same width and height.
        // We need to render 8 rows of rectangles with 8 rectangles in each row.
        // We divide the width screen with 8 to calculate the width and height of each rectangle.

        final int rectangleDimension = ((int) screenWidth / 8);

        RelativeLayout mainLinearLayout = (RelativeLayout) findViewById(R.id.main_layout);
        GridLayout chessboardGridLayout = (GridLayout) findViewById(R.id.chessboard_main_layout);
        GridLayout chessboardPiecesGridLayout = (GridLayout) findViewById(R.id.chessboard_pieces_main_layout);

        EditText maxMovesEdittext = (EditText) findViewById(R.id.max_moves_number_edittext);
        Button movesButton = (Button) findViewById(R.id.moves_button);

        // Render chessboard rectangles.
        boolean pieceColorBoolean = false;
        int pieceColorInt = Color.WHITE;
        int chessboardRectangleIdNumber = 10001;
        for(int number=8;number>0;number--) {
            pieceColorBoolean = !pieceColorBoolean;
            for(int letter=1;letter<9;letter++) {
                if(pieceColorBoolean) {
                    pieceColorInt = Color.parseColor("#c69c6c");
                    pieceColorBoolean = false;
                }
                else {
                    pieceColorInt = Color.parseColor("#764c24");
                    pieceColorBoolean = true;
                }
                ChessboardPiece chessboardRectangle = new ChessboardPiece(getApplicationContext(), Utils.convertPositionToLetter(letter).concat(Integer.toString(number)), pieceColorInt, rectangleDimension, rectangleDimension);
                chessboardRectangle.setId(chessboardRectangleIdNumber);
                chessboardGridLayout.addView(chessboardRectangle);

                chessboardRectangleIdNumber++;
            }
        }

        // Render white pieces
        int chessboardPieceRectangleIdNumber = 20001;
        for(int i=1;i<9;i++) {
            ImageView pawnImageView = new ImageView(getApplicationContext());
            if(i==1 || i==8) {
                pawnImageView.setImageBitmap(resizeMapIcons("rook_white", rectangleDimension, rectangleDimension));
            }
            else if(i==2 || i==7) {
                pawnImageView.setImageBitmap(resizeMapIcons("knight_white", rectangleDimension, rectangleDimension));
            }
            else if(i==3 || i==6) {
                pawnImageView.setImageBitmap(resizeMapIcons("bishop_white", rectangleDimension, rectangleDimension));
            }
            else if(i==4) {
                pawnImageView.setImageBitmap(resizeMapIcons("king_white", rectangleDimension, rectangleDimension));
            }
            else if(i==5) {
                pawnImageView.setImageBitmap(resizeMapIcons("queen_white", rectangleDimension, rectangleDimension));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(rectangleDimension,rectangleDimension);
            pawnImageView.setLayoutParams(params);
            pawnImageView.setId(chessboardPieceRectangleIdNumber);
            chessboardPiecesGridLayout.addView(pawnImageView);

            chessboardPieceRectangleIdNumber++;
        }
        // Render white pawns
        for(int i=1;i<9;i++) {
            ImageView pawnImageView = new ImageView(getApplicationContext());
            pawnImageView.setImageBitmap(resizeMapIcons("pawn_white", rectangleDimension, rectangleDimension));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(rectangleDimension,rectangleDimension);
            pawnImageView.setLayoutParams(params);
            pawnImageView.setId(chessboardPieceRectangleIdNumber);
            chessboardPiecesGridLayout.addView(pawnImageView);

            chessboardPieceRectangleIdNumber++;
        }

        // Render transparent imageviews
        for(int i=0;i<32;i++) {
            ImageView pawnImageView = new ImageView(getApplicationContext());
            pawnImageView.setImageBitmap(resizeMapIcons("transparent", rectangleDimension, rectangleDimension));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(rectangleDimension,rectangleDimension);
            pawnImageView.setLayoutParams(params);
            pawnImageView.setId(chessboardPieceRectangleIdNumber);
            chessboardPiecesGridLayout.addView(pawnImageView);

            chessboardPieceRectangleIdNumber++;
        }

        // Render black pawns
        for(int i=1;i<9;i++) {
            ImageView pawnImageView = new ImageView(getApplicationContext());
            pawnImageView.setImageBitmap(resizeMapIcons("pawn_black", rectangleDimension, rectangleDimension));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(rectangleDimension,rectangleDimension);
            pawnImageView.setLayoutParams(params);
            pawnImageView.setId(chessboardPieceRectangleIdNumber);
            chessboardPiecesGridLayout.addView(pawnImageView);

            chessboardPieceRectangleIdNumber++;
        }
        // Render black pieces
        for(int i=1;i<9;i++) {
            ImageView pawnImageView = new ImageView(getApplicationContext());
            if(i==1 || i==8) {
                pawnImageView.setImageBitmap(resizeMapIcons("rook_black", rectangleDimension, rectangleDimension));
            }
            else if(i==2 || i==7) {
                pawnImageView.setImageBitmap(resizeMapIcons("knight_black", rectangleDimension, rectangleDimension));
            }
            else if(i==3 || i==6) {
                pawnImageView.setImageBitmap(resizeMapIcons("bishop_black", rectangleDimension, rectangleDimension));
            }
            else if(i==4) {
                pawnImageView.setImageBitmap(resizeMapIcons("king_black", rectangleDimension, rectangleDimension));
            }
            else if(i==5) {
                pawnImageView.setImageBitmap(resizeMapIcons("queen_black", rectangleDimension, rectangleDimension));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(rectangleDimension,rectangleDimension);
            pawnImageView.setLayoutParams(params);
            pawnImageView.setId(chessboardPieceRectangleIdNumber);
            chessboardPiecesGridLayout.addView(pawnImageView);

            chessboardPieceRectangleIdNumber++;
        }

        first_white_knight = (ImageView) findViewById(20002);

        maxMovesEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int max_moves_update = Integer.parseInt(editable.toString());
                    Utils.updateMaxMoves(max_moves_update);
                }
                catch (Exception e) {
                    // Do nothing
                }
            }
        });

        movesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.getStartPosition() != null && Utils.getEndPosition() != null && Utils.getMaxMoves() > 0) {
                    Algorithm movesAlgorithm = new Algorithm(Utils.getStartPosition(), Utils.getEndPosition(), Utils.getMaxMoves());
                    final ArrayList<String> moves = movesAlgorithm.start();

                    // We show a dialog for user to choose a sequence of moves
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Choose a sequence of moves!");
                    builder.setItems(moves.toArray(new String[moves.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            moveSequence = moves.get(i);
                            String[] knight_moves = moveSequence.split("->");

                            Toast.makeText(getApplicationContext(), "Applying sequence of moves:\n"+moveSequence, Toast.LENGTH_SHORT).show();

                            // Animating the knight.
                            // Initially we animate the first knight at rectangle B8 to start position
                            float start_knight_x = first_white_knight.getX();
                            float start_knight_y = first_white_knight.getY();

                            System.out.println("[INFO]: "+start_knight_x+","+start_knight_y);

                            int distance_x = -1*(Utils.convertLetterToPosition("B8") - Utils.convertLetterToPosition(Utils.getStartPosition()));
                            int distance_y = Integer.parseInt(String.valueOf("B8".charAt(1))) - Integer.parseInt(String.valueOf(Utils.getStartPosition().charAt(1)));

                            float newX = start_knight_x + distance_x*rectangleDimension;
                            float newY = start_knight_y + distance_y*rectangleDimension;

                            System.out.println("[INFO]: "+newX+","+newY);

                            animations = new TranslateAnimation[knight_moves.length];
                            int animation_counter = 0;

                            /* */
                            TranslateAnimation initial_animation = new TranslateAnimation(0, distance_x*rectangleDimension, 0, distance_y*rectangleDimension);
                            initial_animation.setDuration(1000);
                            initial_animation.setFillAfter(true);
                            initial_animation.setAnimationListener(new KnightAnimationListener(newX, newY));
                            //first_white_knight.startAnimation(initial_animation);

                            // Animate the moves sequence
                            for(int move=1;move<knight_moves.length;move++) {
                                if(move < knight_moves.length-1) {
                                    // We mark intermediate rectangles with blue.
                                    // Start rectangle is marked with green.
                                    // End rectangle is marked with red.
                                    int rectangle_id = 10000 + ((8 - Integer.parseInt(String.valueOf(knight_moves[move].charAt(1))))*8) + Utils.convertLetterToPosition(String.valueOf(knight_moves[move].charAt(0)));
                                    ChessboardPiece intermediate_rectangle = (ChessboardPiece) findViewById(rectangle_id);
                                    intermediate_rectangle.addColorFilter(Color.BLUE);
                                }

                                distance_x = -1*(Utils.convertLetterToPosition(knight_moves[move-1]) - Utils.convertLetterToPosition(knight_moves[move]));
                                distance_y = Integer.parseInt(String.valueOf(knight_moves[move-1].charAt(1))) - Integer.parseInt(String.valueOf(knight_moves[move].charAt(1)));

                                newX = newX + distance_x*rectangleDimension;
                                newY = newY + distance_y*rectangleDimension;

                                System.out.println("[INFO]: "+newX+","+newY);

                                /* */
                                TranslateAnimation intermediate_animation;
                                intermediate_animation = new TranslateAnimation(0, distance_x*rectangleDimension, 0, distance_y*rectangleDimension);
                                intermediate_animation.setDuration(1000);
                                intermediate_animation.setFillAfter(true);
                                intermediate_animation.setStartOffset(1500);
                                intermediate_animation.setAnimationListener(new KnightAnimationListener(newX, newY));
                                //first_white_knight.startAnimation(intermediate_animation);
                                animations[animation_counter] = intermediate_animation;
                                animation_counter++;

                            }
                            first_white_knight.startAnimation(initial_animation);
                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select start position, end position and max number of moves!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void init() {
        Utils.createLetterMapping();

        // Get device's screen width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private class KnightAnimationListener implements Animation.AnimationListener {
        private float newX;
        private float newY;

        public KnightAnimationListener(float x, float y) {
            super();

            this.newX = x;
            this.newY = y;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // Do nothing
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            first_white_knight.clearAnimation();

            first_white_knight.setX(this.newX);
            first_white_knight.setY(this.newY);

            if(playAnimationCounter < animations.length-1) {
                first_white_knight.startAnimation(animations[playAnimationCounter]);
                playAnimationCounter++;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // Do nothing
        }
    }

}
