package com.example.samdollarfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DepositAdapter(val depositList:ArrayList<Deposit>):RecyclerView.Adapter<DepositAdapter.DepositViewHolder>() {
    //ViewHolder 생성 함수,activity_owner 파일의 내용을 상속(?)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepositAdapter.DepositViewHolder {
        val depositview = LayoutInflater.from(parent.context).inflate(R.layout.activity_owner,parent,false)
        return DepositViewHolder(depositview)
    }

    override fun onBindViewHolder(holder: DepositViewHolder, position: Int) {
        holder.username.text=depositList[position].username
        holder.bankname.text=depositList[position].bankname
        holder.inputmoney.text=depositList[position].inputmoney.toString()

    }

    override fun getItemCount(): Int {
        return depositList.size
    }
    //inner class로 TextView연결 :화면에 보여줌
    inner class DepositViewHolder(depositView: View):RecyclerView.ViewHolder(depositView){
        val username = depositView.findViewById<TextView>(R.id.tv_name)
        val bankname = depositView.findViewById<TextView>(R.id.tv_bankname)
        val inputmoney = depositView.findViewById<TextView>(R.id.tv_inputmoney)
    }

}