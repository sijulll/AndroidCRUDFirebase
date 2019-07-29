package pnj.ac.id.uas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pnj.ac.id.uas.Makanan;
import java.util.ArrayList;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.ViewHolder>
{
    private ArrayList<Makanan> listmakanan;
    private Context context;
    FirebaseDataListener listener;

    public AdapterRV(ArrayList<Makanan> makanans, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        listmakanan = makanans;
        context = ctx;
        listener = (ReadDataRealtime)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namamakanan);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.makanan, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Show the data
         */
        final String name = listmakanan.get(position).getName();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.pop_up);
                dialog.setTitle("Want Edit or Delete Data ?");
                dialog.show();
                Button editButton = (Button) dialog.findViewById(R.id.btn_edit_data);
                Button delButton = (Button) dialog.findViewById(R.id.btn_delete_data);

                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(AddItemActivity.getActIntent((Activity) context).putExtra("data", listmakanan.get(position)));
                            }
                        }
                );
                //Button Delete Action
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 *  Kodingan untuk tutorial Selanjutnya :p Delete data
                                 */
                                dialog.dismiss();
                                listener.onDeleteData(listmakanan.get(position), position);
                            }
                        }
                );
                return true;
            }
        });
        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah data pada makanan
         */
        return listmakanan.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Makanan makanan, int position);
    }
}
