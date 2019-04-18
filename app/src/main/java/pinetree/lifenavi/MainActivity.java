package pinetree.lifenavi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycleListView;
    String[] itemTexts = {"入门", "基础", "Cube", "darwStyle", "circle","texture","拉伸方式"};
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        recycleListView = findViewById(R.id.main_recyleview);
        recycleListView.setLayoutManager(new LinearLayoutManager(this));
        recycleListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RAdapter rAdapter = new RAdapter(itemTexts);
        rAdapter.setOnItemClickListener(new RAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int positiion) {
                if (positiion == 0) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, TutoriaPart.class);
                    startActivity(intent);
                } else if (positiion == 1) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, SixStartActivity.class);
                    startActivity(intent);
                } else if (positiion == 2) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, CubeActvity.class);
                    startActivity(intent);
                } else if (positiion == 3) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, StyleActivity.class);
                    startActivity(intent);
                } else if (positiion == 4) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, CircleActivity.class);
                    startActivity(intent);
                }else if (positiion == 5) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, TextureActvity.class);
                    startActivity(intent);
                }else if (positiion == 6) {
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, TextureStyleActvitity.class);
                    startActivity(intent);
                }
            }
        });
        recycleListView.setAdapter(rAdapter);
    }

    private static class RAdapter extends RecyclerView.Adapter<RAdapter.RAdapterHolder> {

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public interface OnItemClickListener {
            void OnItemClickListener(int positiion);
        }

        public RAdapter(String[] list) {
            this.list = list;
        }

        private String[] list;

        @Override
        public RAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyleview_item, parent, false);
            return new RAdapterHolder(v);
        }

        @Override
        public void onBindViewHolder(RAdapterHolder holder, int position) {
            holder.textView.setText(list[position]);
            holder.setPosition(position);
        }


        @Override
        public int getItemCount() {
            return list.length;
        }

        class RAdapterHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            private int position;

            public void setPosition(int position) {
                this.position = position;
            }

            public RAdapterHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.main_recyleview_item);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.OnItemClickListener(position);
                        }
                    }
                });
            }
        }
    }
}
