import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { UserContext } from "../context/UserContext";
import { USERURL, BUGURL } from "../config";

const bugUrl = BUGURL;
const userUrl = USERURL;

const Assigned = () => {
  const { user } = useContext(UserContext);
  const [bugs, setBugs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [updatingBug, setUpdatingBug] = useState(null);
  const [resolutionReports, setResolutionReports] = useState({});
  const [reportedByUsers, setReportedByUsers] = useState({});
  const [feedbackMessage, setFeedbackMessage] = useState("");

  useEffect(() => {
    const fetchBugs = async () => {
      try {
        const response = await axios.get(`${bugUrl}/assigned/${user.id}`);
        const data = response.data;

        if (data && data.bugList) {
          setBugs(data.bugList);

          // Initialize resolutionReports with pre-existing resolution reports
          const initialResolutionReports = data.bugList.reduce((acc, bug) => {
            acc[bug.id] = bug.resolutionReport || "";
            return acc;
          }, {});
          setResolutionReports(initialResolutionReports);

          // Fetch the "reportedBy" users for each bug
          await Promise.all(data.bugList.map((bug) => handleReportedby(bug.reportedBy)));
        } else {
          setBugs([]);
        }
      } catch (err) {
        setError("Failed to fetch bugs.");
      } finally {
        setLoading(false);
      }
    };

    fetchBugs();
  }, [user.id]);

  const handleStatusChange = (bugId, newStatus) => {
    setBugs((prevBugs) =>
      prevBugs.map((bug) =>
        bug.id === bugId ? { ...bug, status: newStatus } : bug
      )
    );
  };

  const handleReportedby = async (reportId) => {
    try {
      const response = await axios.get(`${userUrl}/singleUserById/${reportId}`);
      const userData = response.data;
      const userName = userData.data.name;

      setReportedByUsers((prevState) => ({
        ...prevState,
        [reportId]: userName,
      }));

      return userName;
    } catch (error) {
      console.error("Error fetching reported by user:", error);
    }
  };

  const handleResolutionReportChange = (bugId, report) => {
    setResolutionReports((prevReports) => ({
      ...prevReports,
      [bugId]: report,
    }));
  };

  const handleUpdate = async (bugId) => {
    const updatedBug = bugs.find((bug) => bug.id === bugId);
    const resolutionReport = resolutionReports[bugId];

    // Check if any changes are made to status or resolution report
    if (
      updatedBug.status === updatedBug.originalStatus &&
      resolutionReport === updatedBug.originalResolutionReport
    ) {
      setFeedbackMessage("No changes detected.");
      setTimeout(() => setFeedbackMessage(""), 3000);
      return;
    }

    // Ensure resolution report is not empty if status is RESOLVED
    if (updatedBug.status === "RESOLVED" && !resolutionReport) {
      setFeedbackMessage("Resolution report cannot be empty.");
      setTimeout(() => setFeedbackMessage(""), 3000);
      return;
    }

    // Prepare the update data
    let updateData = {
      status: updatedBug.status,
      resolutionReport: resolutionReport || "",
    };

    if (updatedBug.status === "RESOLVED" && resolutionReport) {
      updateData.resolvedAt = new Date().toISOString().split("T")[0];
    }

    try {
      await axios.patch(`${bugUrl}/${bugId}`, updateData);
      setBugs((prevBugs) =>
        prevBugs.map((bug) =>
          bug.id === bugId ? { ...bug, ...updateData } : bug
        )
      );

      // Reset resolution report after update
      setResolutionReports((prevReports) => ({
        ...prevReports,
        [bugId]: "",
      }));

      setFeedbackMessage("Bug updated successfully!");
      setTimeout(() => setFeedbackMessage(""), 3000);
    } catch (error) {
      console.error("Error updating bug", error);
    }
  };

  if (loading) return <p>Loading bugs...</p>;
  if (error) return <p className="text-red-500">{ error }</p>;

  return (
    <div className="p-6 max-w-5xl mx-auto">
      <h2 className="text-2xl font-bold mb-6">Assigned Bugs</h2>
      {/* Feedback message */ }
      {/* { feedbackMessage && (
        <div className="mb-4 p-3 text-green-600 border border-green-300 rounded">
          { feedbackMessage }
        </div>
      ) } */}
      { feedbackMessage && (
        <div
          className={ `mb-4 p-3 border rounded ${feedbackMessage === "Bug updated successfully!" ? "text-green-600 border-green-300" : "text-red-600 border-red-300"}` }
        >
          { feedbackMessage }
        </div>
      ) }


      {/* Bug List */ }
      <div className="grid grid-cols-1 gap-6">
        { bugs && bugs.length > 0 ? (
          bugs.map((bug) => (
            <div key={ bug.id } className="p-6 border rounded bg-gray-100 shadow-lg">
              <p>Description: { bug.description }</p>

              {/* Bug Info */ }
              <p>
                { reportedByUsers[bug.reportedBy] ? (
                  <span>Assigned by: { reportedByUsers[bug.reportedBy] }</span>
                ) : (
                  <span>Loading user data...</span>
                ) }
              </p>
              <p>
                Status:
                <select
                  value={ bug.status }
                  onChange={ (e) => handleStatusChange(bug.id, e.target.value) }
                  className="ml-2 border rounded p-1"
                  disabled={ updatingBug === bug.id }
                >
                  <option value="OPEN">OPEN</option>
                  <option value="IN_PROGRESS">IN PROGRESS</option>
                  <option value="RESOLVED">RESOLVED</option>
                </select>
              </p>

              {/* Show resolution report input if the status is being set to RESOLVED */ }
              { bug.status === "RESOLVED" && (
                <div>
                  <textarea
                    placeholder="Write resolution report"
                    value={ resolutionReports[bug.id] || "" }
                    onChange={ (e) => handleResolutionReportChange(bug.id, e.target.value) }
                    className="w-full p-2 border rounded mt-2"
                  />
                </div>
              ) }

              {/* Update Button */ }
              <button
                onClick={ () => handleUpdate(bug.id) }
                className="mt-4 px-6 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                disabled={ updatingBug === bug.id } // Disable button while updating
              >
                Update
              </button>
            </div>
          ))
        ) : (
          <p>No bugs found.</p>
        ) }
      </div>
    </div>
  );
};

export default Assigned;
