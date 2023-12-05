package com.example.samdollarfront

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.samdollarfront.databinding.MenuBinding

class MenuAdapter(private val menus: MutableList<Menu>)
    : RecyclerView.Adapter<MenuAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.Holder {
        val binding = MenuBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    fun addMenu(name: String, price: String) {
        val newMenu = Menu(name, price)
        menus.add(newMenu)
        //notifyItemInserted(menus.size - 1)
    }

    fun removeMenu(position: Int) {
        if (position in 0 until menus.size) {
            menus.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menus.size - position)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = menus[position]

        // 데이터 클래스에서 가져온 값으로 각 입력 필드 초기화
        holder.nameInput.setText(menu.name)
        holder.valueInput.setText(menu.price)


        // 입력 필드의 변경사항을 데이터 클래스에 반영
        holder.nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                menu.name = s.toString()
            }
        })

        holder.valueInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                menu.price = s.toString()
            }
        })
        holder.binding.minus.setOnClickListener {
            removeMenu(position)
        }
    }

    override fun getItemCount() = menus.size

    class Holder(val binding: MenuBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameInput: EditText = binding.textName
        val valueInput: EditText = binding.textPrice
    }
}