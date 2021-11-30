package com.notocia.erraya

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import club.cred.synth.views.SynthImageButton
import com.google.android.material.button.MaterialButton
import com.notocia.erraya.adapters.ChatAdapter
import com.notocia.erraya.models.ChatMessage

class ChattingActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var profileNameView: TextView
    private lateinit var buttonBack: SynthImageButton
    private lateinit var chatExpText: TextView
    private lateinit var buttonSend: MaterialButton
    private lateinit var chatList: RecyclerView
    private lateinit var chatTextBox: EditText

    private var profileId: Int = -1
    private var imageResource: Int = 0
    private var profileName: String = "NA"
    private var profileType: Int = -1

    private val chatMessageList: ArrayList<ChatMessage> = ArrayList()
    private lateinit var chatListAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO:Enable bellow code
        //window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_chat)
        profileImageView = findViewById(R.id.messageProfileImage)
        profileNameView = findViewById(R.id.textWindowTitle)
        chatExpText = findViewById(R.id.chatExpText)
        buttonBack = findViewById(R.id.buttonBack)
        buttonSend = findViewById(R.id.buttonSend)
        chatList = findViewById(R.id.chatList)
        chatTextBox = findViewById(R.id.sendMessage)

        buttonBack.setOnClickListener(backListener)
        buttonSend.setOnClickListener(sendButtonListener)
        initChatWindow()
        initChatMessageList()
        chatListAdapter = ChatAdapter(chatMessageList)
        chatList.adapter = chatListAdapter
    }

    val backListener = View.OnClickListener {
        onBackPressed()
    }

    private var sendButtonListener = View.OnClickListener {
        val msg: String = chatTextBox.text.toString()
        if (msg.isNotBlank()) {
            //TODO: Perform send here
            val cm = ChatMessage()
            cm.addressTo = 0
            cm.from = 5
            cm.messageAction = 1
            cm.message = msg
            chatMessageList.add(cm)
            chatListAdapter.notifyItemInserted(chatMessageList.size)
            chatTextBox.text.clear()
        }
    }

    private fun initChatWindow() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            profileId = bundle["profileId"] as Int
            imageResource = bundle["imageResource"] as Int
            profileName = bundle["profileName"] as String
            profileType = bundle["profileType"] as Int
            chatExpText.text = "How was your experience with $profileName?"
            profileImageView.setImageResource(imageResource)
            profileNameView.text = profileName
        }
    }

    private fun initChatMessageList() {
        //Get list of message form database
        val cm = ChatMessage()
        cm.addressTo = 1
        cm.from = 5
        cm.message = "Hi there"
        cm.messageAction = 0 //Message received
        chatMessageList.add(cm)
    }
}