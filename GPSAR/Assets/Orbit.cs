using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Orbit : MonoBehaviour {
    private float x, y, z;
    private int fb;

	// Use this for initialization
	void Start () {
        x = Random.Range(-100, 100);
        y = Random.Range(-100, 100);
        fb = Random.Range(0,6);
        switch (fb)
        {
            case 0:
                z = -280;
                break;
            case 1:
                z = 280;
                break;
            case 2:
                z = -560;
                break;
            case 3:
                z = 560;
                break;
            case 4:
                z = -960;
                break;
            case 5:
                z = 960;
                break;
        }
        
	}
	
	// Update is called once per frame
	void Update () {
        transform.Rotate(Vector3.up, 100 * Time.deltaTime);
        transform.position = new Vector3(x, y, z);
	}
}
