package com.test.flapicall.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.test.flapicall.R;
import com.test.flapicall.api.models.Exam;

import java.util.ArrayList;

/**
 * Created by orly on 9/11/17.
 */

public class ExamsAdapter extends ArrayAdapter<Exam> {

  private TextView               _examText;
  private RoundCornerProgressBar _examProgress;

  private ArrayList<Exam> skillList = new ArrayList<>();

  public ExamsAdapter(@NonNull Context context, @LayoutRes int resource,
      ArrayList<Exam> skillList) {
    super(context, 0);
    this.skillList = skillList;
  }

  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    Exam exam = skillList.get(position);
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_exam, parent, false);
    }
    _examText = (TextView) convertView.findViewById(R.id.text_exam);
    _examProgress = (RoundCornerProgressBar) convertView.findViewById(R.id.progress_exam);

    setItems(exam);
    return convertView;
  }

  private void setItems(Exam exam) {
    _examText.setText(exam.getName());
    _examProgress.setProgress(exam.getProgress());
  }

  @Override
  public int getCount() {
    return skillList.size();
  }
}
