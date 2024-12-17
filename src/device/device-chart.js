import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { Bar } from "react-chartjs-2";
import "chart.js/auto";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

function DeviceChart() {
  const { deviceId } = useParams();
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [chartConfig, setChartConfig] = useState({
    labels: [],
    datasets: [
      {
        label: "Measurement Data",
        data: [],
        backgroundColor: "rgba(153,102,255,0.6)",
        borderColor: "rgba(153,102,255,1)",
        borderWidth: 2,
      },
    ],
  });

  useEffect(() => {
    const fetchDeviceMeasurements = async () => {
      try {
        const formattedDate = selectedDate.toISOString().split("T")[0];
        const { data } = await axios.get(
          `http://localhost:5001/measurements/${deviceId}/${formattedDate}`
        );

        const processedData = data.map((entry) => ({
          x: new Date(Number(entry.timestamp_value)),
          y: Number(entry.measurement),
        }));

        setChartConfig((prev) => ({
          ...prev,
          labels: processedData.map((item) => item.x),
          datasets: [
            {
              ...prev.datasets[0],
              data: processedData,
            },
          ],
        }));
      } catch (error) {
        console.error("Failed to fetch measurements:", error);
      }
    };

    fetchDeviceMeasurements();
  }, [deviceId, selectedDate]);

  const onDateChange = (date) => setSelectedDate(date);

  return (
    <div style={{ padding: "20px", textAlign: "center" }}>
      <h1>Device Data Visualization</h1>
      <p>Device ID: <strong>{deviceId}</strong></p>
      <DatePicker
        selected={selectedDate}
        onChange={onDateChange}
        dateFormat="yyyy-MM-dd"
        maxDate={new Date()}
        className="custom-date-picker"
        popperPlacement="bottom"
      />
      <div style={{ marginTop: "30px" }}>
        <Bar
          data={chartConfig}
          options={{
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
              legend: {
                display: true,
                position: "top",
              },
            },
            scales: {
              x: {
                type: "time",
                time: {
                  unit: "minute",
                  tooltipFormat: "yyyy-MM-dd HH:mm",
                },
                title: {
                  display: true,
                  text: "Time",
                  color: "#333",
                },
              },
              y: {
                title: {
                  display: true,
                  text: "Measurements",
                  color: "#333",
                },
              },
            },
          }}
          height={400}
        />
      </div>
    </div>
  );
}

export default DeviceChart;
