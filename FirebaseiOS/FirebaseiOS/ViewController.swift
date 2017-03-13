//
//  ViewController.swift
//  FirebaseiOS
//
//  Created by Alvaro David on 12/03/17.
//  Copyright © 2017 AlvarDev. All rights reserved.
//

import UIKit
import Firebase
import FirebaseDatabase

class ViewController: UIViewController {
  
    
    @IBOutlet weak var typeMessage: UITextField!
    @IBOutlet weak var messages: UITextView!
    
    
    let rootRef = FIRDatabase.database().reference()
    
    override func viewDidLoad() {
        super.viewDidLoad()
   
    }
    
    override func viewWillAppear(_ animated: Bool) {
        let message = rootRef.child("message")
        
        message.observe(FIRDataEventType.value, with: { (snapshot) in
            let chat = snapshot.value as? String
            self.messages.text = chat
        })
    }

    @IBAction func sendMessage(_ sender: Any) {
        if (typeMessage.text?.characters.count)! > 0{
            let message = (messages.text.characters.count == 0 ? "" : messages.text + "\n") +
                "iOS: " + typeMessage.text!
            typeMessage.text = ""
            rootRef.child("message").setValue(message)
        }else{
            print("Message is empty")
        }
    }
    
}

extension ViewController: UITextFieldDelegate {
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        return textField.resignFirstResponder()
    }
    
}

