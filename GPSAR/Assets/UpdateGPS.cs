using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UpdateGPS : MonoBehaviour
{
    public Text coordinates;

    private void Update()
    {
        coordinates.text = "현재위치:" + GPS.Instance.region_name.ToString() +"    "+ "지역번호:" + GPS.Instance.region_code.ToString();

    }
}