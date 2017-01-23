package com.rivigo.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 23-01-2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CellViewHolder> {
    Character[][] cellValues;
    Context context;
    int xCount = 0, oCount = 0;

    public MyAdapter(Character[][] cellValues, Context context) {
        this.cellValues = cellValues;
        this.context = context;
    }

    @Override
    public CellViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.cell, viewGroup, false);
        return new CellViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final CellViewHolder cellHolder, final int position) {
        final int row = position / 3;
        final int column = position % 3;
        final Character c = cellValues[row][column];
        cellHolder.populateItem(c);
        cellHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cellValues[row][column] != null) return;
                if (xCount == oCount) {
                    cellValues[row][column] = 'X';
                    cellHolder.tvValue.setText(String.valueOf('X'));
                    xCount++;
                } else {
                    cellHolder.tvValue.setText(String.valueOf('O'));
                    cellValues[row][column] = 'O';
                    oCount++;
                }
                if (checkIfWinnerFound()) {
                    Toast.makeText(context, "You have won this!", Toast.LENGTH_LONG).show();
                    cellValues = new Character[3][3];
                    notifyDataSetChanged();
                }
            }
        });


    }

    private boolean checkIfWinnerFound() {
        // for every row
        for (int i = 0; i < 3; i++) {
            boolean found = true;
            for (int j = 0; j < 3 - 1; j++) {
                if (cellValues[i][j] ==null || cellValues[i][j + 1]==null || cellValues[i][j] != cellValues[i][j + 1]) {
                    found = false;
                    break;
                }
            }
            if (found==true){
                return true;
            }
        }

        // for columns
        for (int j = 0; j < 3; j++) {
            boolean found = true;
            for (int i = 0; i < 3 - 1; i++) {
                if (cellValues[i][j]==null || cellValues[i+1][j]==null || (cellValues[i][j] != cellValues[i+1][j])) {
                    found = false;
                    break;
                }
            }
            if (found==true){
                return true;
            }
        }

        boolean winnerFound = true;
        for (int i=0;i<3-1;i++){
            if (cellValues[i][i]==null || cellValues[i+1][i+1]==null || (cellValues[i][i]!=cellValues[i+1][i+1])){
                winnerFound = false;
                break;
            }
        }

        if (winnerFound) return winnerFound;
        winnerFound = true;
        for (int i=0;i<3-1;i++){
            if (cellValues[i][3-1-i]==null || cellValues[i+1][3-1-(i+1)]==null || cellValues[i][3-1-i]!=cellValues[i+1][3-1-(i+1)]){
                winnerFound = false;
                break;
            }
        }
        return winnerFound;
    }

    @Override
    public int getItemCount() {
        return cellValues.length * cellValues[0].length;
    }

    public static class CellViewHolder extends RecyclerView.ViewHolder {

        TextView tvValue;
        View layout;

        public CellViewHolder(View itemView) {
            super(itemView);
            tvValue = (TextView) itemView.findViewById(R.id.tv_value);
            layout = itemView;
        }

        public void populateItem(Character c) {
            tvValue.setText(c==null?"":c.toString());
        }
    }
}
